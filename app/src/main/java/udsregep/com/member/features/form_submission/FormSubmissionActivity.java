package udsregep.com.member.features.form_submission;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.JsonObject;;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import android.Manifest;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import udsregep.com.member.R;
import udsregep.com.member.base.BaseActivity;
import udsregep.com.member.utils.CallbackInterface;
import udsregep.com.member.utils.Helper;
import udsregep.com.member.utils.Utility;


/**
 * Created by agustinaindah on 13/07/2017.
 */

public class FormSubmissionActivity extends BaseActivity implements FormSubmissionPresenter.View {

    private static final int GALLERY_REQUEST = 565;
    private static final int REQUEST_CAMERA = 456;

    @BindView(R.id.edtFormOrderNama)
    EditText edtFormOrderNama;
    /*@BindView(R.id.edtFormOrderNamaPerusahaan)
    EditText edtFormOrderNamaPerusahaan;*/
    @BindView(R.id.edtFormOrderNoHP)
    EditText edtFormOrderNoHP;
    @BindView(R.id.edtFormOrderAlamat)
    EditText edtFormOrderAlamat;
    @BindView(R.id.edtKeterangan)
    EditText edtKeterangan;
    @BindView(R.id.imgFormOrder)
    ImageView imgFormOrder;
    @BindView(R.id.btnUpload)
    Button btnUpload;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @BindString(R.string.label_form_pengambilan)
    String strFormPengambilan;
    @BindString(R.string.loading)
    String strloading;
    @BindString(R.string.info)
    String strInfo;
    @BindString(R.string.msg_confirmed)
    String strSuccessConfirm;
    @BindString(R.string.msg_empty)
    String strMsgEmpty;
    @BindString(R.string.label_take_photo)
    String strTakePhoto;
    @BindString(R.string.label_choose_gallery)
    String strChooseGallery;
    @BindString(R.string.label_cancel)
    String strCancel;
    @BindString(R.string.label_add_photo)
    String strAddPhoto;

    private ProgressDialog progressDialog;
    private FormSubmissionPresenter mPresenter;
    private GalleryPhoto galleryPhoto;
    private String fileEvidence;
    private String newPhoto = null;
    private String userChoosenTask;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(strFormPengambilan);

        mPresenter = new FormSubmissionPresenterImpl(this);
        galleryPhoto = new GalleryPhoto(this);

        initProgress();

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("Tes", "Permission to record denied");
            makeRequest();
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                GALLERY_REQUEST);
    }

    private void initProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(strloading);
    }

    @OnClick(R.id.btnUpload)
    public void clickUploadPhoto(View view) {
        final CharSequence[] items = {strTakePhoto, strChooseGallery,
                strCancel};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormSubmissionActivity.this);
        builder.setTitle(strAddPhoto);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(FormSubmissionActivity.this);
                if (items[item].equals(strTakePhoto)) {
                    userChoosenTask = strTakePhoto;
                    if (result)
                        cameraIntent();
                } else if (items[item].equals(strChooseGallery)) {
                    userChoosenTask = strChooseGallery;
                    if (result)
                        galleryIntent();
                } else if (items[item].equals(strCancel)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void galleryIntent() {
        /*startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, GALLERY_REQUEST);
        }
    }

    private void cameraIntent() {
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @OnClick(R.id.btnSubmit)
    public void submit(View view) {
        mPresenter.postInquiry(getInput());
    }

    @Override
    protected int setView() {
        return R.layout.activity_form_submission;
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Helper.createAlert(this, strInfo, msg);
    }

    @Override
    public void notConnect(String msg) {
        Helper.createAlert(this, strInfo, "Tidak ada jaringan");
    }

    @Override
    public boolean validate() {
        edtFormOrderNama.setError(null);
        edtFormOrderNoHP.setError(null);
        edtFormOrderAlamat.setError(null);
        /*edtKeterangan.setError(null);*/

        boolean cancel = false;
        View focus = null;

        if (TextUtils.isEmpty(getInput().get("name").getAsString())) {
            edtFormOrderNama.setError(strMsgEmpty);
            focus = edtFormOrderNama;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("phone").getAsString())) {
            edtFormOrderNoHP.setError(strMsgEmpty);
            focus = edtFormOrderNoHP;
            cancel = true;
        }
        if (TextUtils.isEmpty(getInput().get("address").getAsString())) {
            edtFormOrderAlamat.setError(strMsgEmpty);
            focus = edtFormOrderAlamat;
            cancel = true;
        }
        /*if (TextUtils.isEmpty(getInput().get("note").getAsString())) {
            edtKeterangan.setError(strMsgEmpty);
            focus = edtKeterangan;
            cancel = true;
        }*/
        if (cancel) {
            focus.requestFocus();
        }
        return cancel;
    }

    private JsonObject getInput() {
        JsonObject jsonInput = new JsonObject();
        try {
            jsonInput.addProperty("name", edtFormOrderNama.getText().toString());
            jsonInput.addProperty("phone", edtFormOrderNoHP.getText().toString());
            jsonInput.addProperty("address", edtFormOrderAlamat.getText().toString());
            jsonInput.addProperty("note", edtKeterangan.getText().toString());
            jsonInput.addProperty("image", fileEvidence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInput;
    }

    @Override
    public void success(JsonObject jsonData) {
        Helper.createAlert(this, strInfo, strSuccessConfirm, false, new CallbackInterface() {
            @Override
            public void callback() {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();
                try {
                    Bitmap bitmap = ImageLoader
                            .init()
                            .from(photoPath)
                            .requestSize(512, 512)
                            .getBitmap();
                    imgFormOrder.setImageBitmap(bitmap);
                    newPhoto = photoPath;
                    if (newPhoto != null) {
                        try {
                            ImageLoader imgLoader = ImageLoader
                                    .init()
                                    .from(newPhoto);
                            Bitmap newPhoto = imgLoader.requestSize(1024, 1024).getBitmap();
                            fileEvidence = ImageBase64.encode(newPhoto);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_CAMERA)
            onCaptureImageResult(data);*/
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        galleryPhoto.setPhotoUri(data.getData());
        String photoPath = galleryPhoto.getPath();
        try {
            Bitmap bitmap = ImageLoader
                    .init()
                    .from(photoPath)
                    .requestSize(512, 512)
                    .getBitmap();
            imgFormOrder.setImageBitmap(bitmap);
            newPhoto = photoPath;
            if (newPhoto != null) {
                try {
                    ImageLoader imgLoader = ImageLoader
                            .init()
                            .from(newPhoto);
                    Bitmap newPhoto = imgLoader.requestSize(130, 130).getBitmap();
                    fileEvidence = ImageBase64.encode(newPhoto);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, null, null);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte [] newImage = bytes.toByteArray();

        imgFormOrder.setImageBitmap(thumbnail);

        fileEvidence = Base64.encodeToString(newImage, Base64.DEFAULT);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals(strTakePhoto))
                        cameraIntent();
                    else if (userChoosenTask.equals(strChooseGallery))
                        galleryIntent();
                } else {
                    //code for deny
                    Log.i("Tes", "Permission has been granted by user");
                }
                break;
        }
    }


    /*@Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GALLERY_REQUEST: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i("tes", "Permission has been denied by user");
                } else {
                    Log.i("Tes", "Permission has been granted by user");
                }
                return;
            }
        }
    }*/

}
