package com.bcm.account.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.self_fragment_page.activity_self_modify;
import com.bcm.account.self_fragment_page.activity_self_signup;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Bean on 2017/4/10.
 */

public class SelfFragment extends Fragment {
    String imageUrl = "http://content.52pk.com/files/100623/2230_102437_1_lit.jpg";
    private View view;
    TextView logout, name, person_age, person_age_go, name_go, signdetails, gender, gender_go,
            place, place_go,title;
    SimpleDraweeView logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_self_fragment, null);
        logout = (TextView) view.findViewById(R.id.sign_logout);
        name = (TextView) view.findViewById(R.id.person_name);
        name_go = (TextView) view.findViewById(R.id.person_name_go);
        signdetails = (TextView) view.findViewById(R.id.person_sign);
        person_age = (TextView) view.findViewById(R.id.person_birth);
        person_age_go = (TextView) view.findViewById(R.id.person_birth_go);
        gender = (TextView) view.findViewById(R.id.person_gender);
        gender_go = (TextView) view.findViewById(R.id.person_gender_go);
        place = (TextView) view.findViewById(R.id.person_place);
        place_go = (TextView) view.findViewById(R.id.person_place_go);
        logo = (SimpleDraweeView) view.findViewById(R.id.person_logo);
        title=(TextView)view.findViewById(R.id.header_title);
        setClickListener();
        title.setText("个人");


        return view;
    }

    public void setClickListener() {
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();

                }
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_self_modify.class);
                intent.putExtra("title", "修改昵称");
                startActivity(intent);
            }
        });
        name_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_self_modify.class);
                intent.putExtra("title", "修改昵称");
                startActivity(intent);
            }
        });
        signdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_self_modify.class);
                intent.putExtra("title", "修改签名");
                startActivity(intent);
            }
        });
        gender_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请选择性别");
                final String[] sex = {"男", "女", "保密"};
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder.setSingleChoiceItems(sex, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender.setText(sex[which]);
                        updatemaessge(sex[which]);
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请选择性别");
                final String[] sex = {"男", "女", "保密"};
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder.setSingleChoiceItems(sex, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender.setText(sex[which]);
                        updatemaessge(sex[which]);
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        person_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_self_modify.class);
                intent.putExtra("title", "修改年龄");
                startActivity(intent);
            }

        });
        person_age_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_self_modify.class);
                intent.putExtra("title", "修改年龄");
                startActivity(intent);
            }

        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_self_modify.class);
                intent.putExtra("title", "修改所在地");
                startActivity(intent);
            }

        });
        place_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_self_modify.class);
                intent.putExtra("title", "修改所在地");
                startActivity(intent);
            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                checkuser();
            }
        });
    }

    public void load_information() {
        String username = (String) BmobUser.getObjectByKey("username");
        String age = (String) BmobUser.getObjectByKey("age");
        String details = (String) BmobUser.getObjectByKey("signDetails");
        String location = (String) BmobUser.getObjectByKey("location");
        myUser currentuser = BmobUser.getCurrentUser(myUser.class);
        BmobFile file = currentuser.getpic();
        try
        {
            String url=file.getFileUrl();
            logo.setImageURI(url);
        }catch(NullPointerException e){

        }

        signdetails.setText(details);
        person_age.setText(age);
        name.setText(username);
        place.setText(location);
    }

    public void checkuser() {
        BmobUser currentuser = BmobUser.getCurrentUser(myUser.class);
        if (currentuser != null) {
            load_information();
        } else {
            getActivity().finish();
            Intent intent = new Intent(getActivity(), activity_self_signup.class);
            startActivity(intent);
        }
    }

    public void updatemaessge(String s) {
        myUser newUser = new myUser();
        newUser.setGender(s);
        BmobUser bmobUser = BmobUser.getCurrentUser(myUser.class);
        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                } else {
                    Toast.makeText(getActivity(), "更新用户信息失败:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= 19) {
                handleImageOnKitKat(data);
            } else {
                handleImagebeforeKitKat(data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "请求被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void handleImagebeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        Toast.makeText(getActivity(), imagePath + "2", Toast.LENGTH_LONG).show();
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagepath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagepath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                Toast.makeText(getActivity(), " doucuments1", Toast.LENGTH_LONG).show();
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagepath = getImagePath(contenturi, null);
                Toast.makeText(getActivity(), " doucuments2", Toast.LENGTH_LONG).show();
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagepath = getImagePath(uri, null);
            Toast.makeText(getActivity(), " content", Toast.LENGTH_LONG).show();
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagepath = uri.getPath();
            Toast.makeText(getActivity(), " file", Toast.LENGTH_LONG).show();
        }
        final String picPath = imagepath;
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    myUser newUser = new myUser();
                    newUser.setpic(bmobFile);
                    BmobUser bmobUser = BmobUser.getCurrentUser(myUser.class);
                    newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                myUser currentuser = BmobUser.getCurrentUser(myUser.class);
                                BmobFile file = currentuser.getpic();
                                try
                                {
                                    String url=file.getFileUrl();
                                    logo.setImageURI(url);
                                }catch(NullPointerException exec){

                                }
                            } else {
                                Toast.makeText(getActivity(), "更新用户信息失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "上传文件失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });

    }

    public String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkuser();
    }
}
