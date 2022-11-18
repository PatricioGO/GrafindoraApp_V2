package santoTomas.grafindora_appv2;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrimerFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ImageView imgPerfil;
    private FirebaseStorage storage;
    private StorageReference mStorage;
    final int CAPTURA_IMAGEN = 1;
    private static final int GALLERY_INTENT = 1;
    private ProgressDialog mProgresDialog;
    String []archivos;

    public PrimerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PrimerFragment newInstance(String param1, String param2) {
        PrimerFragment fragment = new PrimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage= FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
       StorageReference fotoPerfil = storageRef.child("fotosPerfil.jpg");

        try {
            File localfile = File.createTempFile("images","jpg");
            fotoPerfil.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(getContext())
                            .load(localfile)
                            .fitCenter()
                            .centerCrop()
                            .into(imgPerfil);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        archivos= getContext().fileList();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_primer, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nombre = view.findViewById(R.id.tvnombrepet);
        TextView edad = view.findViewById(R.id.tvedad);
        TextView raza = view.findViewById(R.id.tvraza);
        TextView sexo = view.findViewById(R.id.tvsexo);
        Button btnCamara= view.findViewById(R.id.btncam);
        imgPerfil = view.findViewById(R.id.imgperfil);
        mProgresDialog = new ProgressDialog(getContext());

        mStorage= FirebaseStorage.getInstance().getReference();
        
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

        try {
            SQLiteDatabase db = getContext().openOrCreateDatabase("DB_GRAFIN",Context.MODE_PRIVATE ,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS mascota(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre VARCHAR,edad VARCHAR,raza VARCHAR,sexo VARCHAR)");

            final Cursor c = db.rawQuery("select * from mascota",null);
            c.moveToLast();
            int id = c.getColumnIndex("id");
            int nom = c.getColumnIndex("nombre");
            int ed = c.getColumnIndex("edad");
            int raz = c.getColumnIndex("raza");
            int sex = c.getColumnIndex("sexo");

            Mascota mascota = new Mascota();

            mascota.id = c.getInt(id);
            mascota.nombre = c.getString(nom).toString();
            mascota.edad = c.getString(ed).toString();
            mascota.raza = c.getString(raz).toString();
            mascota.sexo = c.getString(sex).toString();

            nombre.append(" "+mascota.nombre);
            edad.append(" "+mascota.edad);
            raza.append(" " +mascota.raza);
            sexo.append(" "+mascota.sexo);


        }catch (Exception ex){}


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode == RESULT_OK){

            mProgresDialog.setTitle("Subiedo...");
            mProgresDialog.setMessage("Subiendo foto a FireBase");
            mProgresDialog.setCancelable(false);
            mProgresDialog.show();

            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("fotosPerfil.jpg");

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgresDialog.dismiss();

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getContext())
                                    .load(uri)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(imgPerfil);
                        }
                    });

                    Toast.makeText(getContext(),"Foto subida correctamente",Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

}