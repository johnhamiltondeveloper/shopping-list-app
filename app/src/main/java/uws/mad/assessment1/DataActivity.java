package uws.mad.assessment1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;
import java.util.jar.Attributes;

public class DataActivity extends AppCompatActivity {

    private DatabaseManager mydManager;
    private String ID;
    private int mode;
    private String table;
    private int tableInt;

    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        mydManager = new DatabaseManager(DataActivity.this);

        final Button button = findViewById(R.id.data_save);
        final EditText name = findViewById(R.id.NameInput);
        final EditText price = findViewById(R.id.PriceInput);
        final EditText quinary = findViewById(R.id.QunityInput);
        final EditText location = findViewById(R.id.LocationInput);
        final Button remove = findViewById(R.id.Remove);
        final ImageButton editImageButton = findViewById(R.id.editImageButton);
        final ImageView imageView = findViewById(R.id.DisplayImage);
        final Button CancelButton = findViewById(R.id.CancelButton);

        Button moveto = findViewById(R.id.MoveToPantry);
        CardView DisplayPantryMove = findViewById(R.id.DisplayPantryMove);

        Intent intent = getIntent();
        table = intent.getStringExtra("table");
        mode = intent.getIntExtra("mode", 0);
        ID = intent.getStringExtra("id");
        tableInt = intent.getIntExtra("showmove",1);

        if(tableInt == 0) {
            DisplayPantryMove.setVisibility(View.VISIBLE);
        }

        if (mode == 0) {
            ID = UUID.randomUUID().toString();
            button.setText("add");
        }


        if (mode == 1) {

            item data = mydManager.retrieveItem(table, ID);

            name.setText(data.getName());
            price.setText(String.valueOf(data.getPrice()));
            quinary.setText(String.valueOf(data.getQuantity()));
            location.setText(data.getLocation());
            remove.setVisibility(View.VISIBLE);
            ImageView imagedata =  findViewById(R.id.DisplayImage);

            image = data.image;

            try {
                byte[] encodebytes = Base64.decode(data.image, Base64.DEFAULT);
                Bitmap BitImage = BitmapFactory.decodeByteArray(encodebytes, 0, encodebytes.length);

                imagedata.setImageBitmap(BitImage);
            }
            catch(Exception e){
                e.printStackTrace();
            }

            button.setText("update");

        }

        moveto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mydManager.removeRow("shopping", ID);
                mydManager.addRow("pantry", ID, name.getText().toString(), Integer.parseInt(price.getText().toString()), Integer.parseInt(quinary.getText().toString()), location.getText().toString(), image);
                Toast.makeText(getApplicationContext(),"Moved item to pantry",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mode == 0) {
                    mydManager.addRow(table, ID, name.getText().toString(), Integer.parseInt(price.getText().toString()), Integer.parseInt(quinary.getText().toString()), location.getText().toString(), image);
                    Toast.makeText(getApplicationContext(),"Created new item in " + table,Toast.LENGTH_LONG).show();
                }

                if (mode == 1) {
                    mydManager.updateRow(table, ID, name.getText().toString(), Integer.parseInt(price.getText().toString()), Integer.parseInt(quinary.getText().toString()), location.getText().toString(), image);
                    Toast.makeText(getApplicationContext(),"Updated item in " + table,Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydManager.removeRow(table, ID);

                Toast.makeText(getApplicationContext(),"removed item from " + table,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ImageView img = findViewById(R.id.DisplayImage);

                try {

                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 256, 256, true);

                    img.setImageBitmap(resized);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    resized.compress(Bitmap.CompressFormat.PNG,100,bos);
                    byte[] b = bos.toByteArray();
                    image = Base64.encodeToString(b,Base64.DEFAULT);

                }
                catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
