package com.example.damthixuanminh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class KTM extends AppCompatActivity {
    RadioGroup rdg;
    private static final int code = 100;
    RadioButton rdgoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhktm);
        rdg = (RadioGroup) findViewById(R.id.rdg);
        rdgoi = (RadioButton) findViewById(R.id.goidien);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedID) {
                if (checkedID == R.id.goidien) {

                    Toast.makeText(KTM.this, "Ban vua chon goi dien", Toast.LENGTH_SHORT).show();
                    String sodt = "tel:0942160880";
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:04322523"));
                    startActivity(intent);
                } else if (checkedID == R.id.hienthiweb) {

                    Toast.makeText(KTM.this, "Ban chon hien thi web", Toast.LENGTH_SHORT).show();
                    String website = "dantri,com.vn";
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                    startActivity(intent1);
                } else if (checkedID == R.id.searchweb) {
                    Intent intent2 = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent2.putExtra("query", "AI");
                    startActivity(intent2);
                } else if (checkedID == R.id.guitinnhan) {

                    String data = "smsto: 0988768966";
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse(data));
                    i.putExtra("sms_body", "xin chao");
                    startActivity(i);
                } else if (checkedID == R.id.nhac) {
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_APP_MUSIC);
                    try {
                        startActivity(i);
                        Toast.makeText(KTM.this, "Đang mở ứng dụng âm nhạc", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(KTM.this, "Không tìm thấy ứng dụng âm nhạc", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                } else if (checkedID == R.id.image) {
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType("image/*");
                    startActivity(i);
                } else if (checkedID == R.id.Camera) {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, code);
                    //startActivity(i);
                }
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==code && resultCode==RESULT_OK){
            Bitmap anh=(Bitmap) data.getExtras().get("data");
            ImageView img= findViewById(R.id.imv);
            img.setImageBitmap(anh);
        }
    }
}
