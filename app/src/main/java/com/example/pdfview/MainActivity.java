package com.example.pdfview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button button;
    Spinner item1Spinner, item2Spinner,item3Spinner,item4Spinner,item5Spinner;
    EditText customerName, phoneNo,area, qty1, qty2,qty3, qty4, qty5;
    Bitmap bitmap, scaledBitmap;
    int pageWidth = 1200;
    Date dateObj;
    DateFormat dateFormat;
    float[]prices = new float[]{0, 200, 300, 450, 325, 500};



/*
    String[] informationArray = new String[]{"Name", "Company Name", "Address", "Phone", "Email"};
*/


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.pdf);
        item1Spinner = findViewById(R.id.item1Spinner);
        item2Spinner = findViewById(R.id.item2Spinner);
        item3Spinner = findViewById(R.id.item3Spinner);
        item4Spinner = findViewById(R.id.item4Spinner);
        item5Spinner = findViewById(R.id.item5Spinner);
        customerName = findViewById(R.id.editTextCustomerName);
        phoneNo = findViewById(R.id.editTextPhone);
        area = findViewById(R.id.editTextArea);
        qty1 = findViewById(R.id.editTxtQty1);
        qty2 = findViewById(R.id.editTxtQty2);
        qty3 = findViewById(R.id.editTxtQty3);
        qty4 = findViewById(R.id.editTxtQty4);
        qty5 = findViewById(R.id.editTxtQty5);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        scaledBitmap = Bitmap.createScaledBitmap(bitmap,300, 300, false);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        offlinePDFClick();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void offlinePDFClick() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dateObj = new Date();

                if (customerName.getText().toString().length()==0 ||
                        phoneNo.getText().toString().length()==0 ||
                        area.getText().toString().length()==0 ||
                        qty1.getText().toString().length()==0 ||
                        qty2.getText().toString().length()==0) {
                    Toast.makeText(MainActivity.this, "Some Field Empty", Toast.LENGTH_LONG).show();
                } else {


                    PdfDocument myPdfDocument = new PdfDocument();
                    Paint myPaint = new Paint();
                    Paint titlePaint = new Paint();



                    PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                    Canvas canvas = myPage1.getCanvas();

                    canvas.drawBitmap(scaledBitmap, 0, 0, myPaint);

            /*titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(70);
            canvas.drawText("Bill Pad", pageWidth/2, 270, titlePaint);


*/

                    myPaint.setColor(Color.BLACK);
                    myPaint.setTextSize(40f);
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    canvas.drawText("MY OWN GARDEN", 20, 340, myPaint);
                    canvas.drawText("Plot 48,2nd Main Rd,Babu Nagar", 20, 400, myPaint);
                    canvas.drawText("Chennai", 20, 460, myPaint);
                    canvas.drawText("Phone :+95003 08464", 20, 520, myPaint);
                    canvas.drawText("Phone :+87787 24296", 20, 580, myPaint);

           /* titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            titlePaint.setTextSize(70);
            canvas.drawText("Invoice", pageWidth/2, 500, titlePaint);*/

                    myPaint.setColor(Color.BLACK);
                    myPaint.setTextSize(40f);
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    canvas.drawText(""+customerName.getText(), pageWidth-20, 100, myPaint);
                    canvas.drawText(""+area.getText(), pageWidth-20, 150, myPaint);
                    canvas.drawText(""+phoneNo.getText(), pageWidth-20, 210, myPaint);

                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("Invoice No.:" +"232425", pageWidth-20, 590, myPaint);

                    dateFormat = new SimpleDateFormat("dd/MM/yy");
                    canvas.drawText("Date: " + dateFormat.format(dateObj), pageWidth-20, 640, myPaint);

                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    canvas.drawText("Time: " + dateFormat.format(dateObj), pageWidth-20, 690, myPaint);

                    myPaint.setStyle(Paint.Style.STROKE);
                    myPaint.setStrokeWidth(2f);
                    canvas.drawRect(20, 780, pageWidth-20, 860, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setStyle(Paint.Style.FILL);
                    myPaint.setTextSize(30f);
                    canvas.drawText("S.No.", 20, 830, myPaint);
                    canvas.drawText("MRP", 110, 830, myPaint);
                    canvas.drawText("Item Description", 200, 830, myPaint);
                    canvas.drawText("Base Rate", 500, 830, myPaint);
                    canvas.drawText("Price", 700, 830, myPaint);
                    canvas.drawText("Qty", 900, 830, myPaint);
                    canvas.drawText("Total", 1050, 830, myPaint);

                    canvas.drawLine(180, 780, 180, 1220, myPaint);
                    canvas.drawLine(100, 780, 100, 1220, myPaint);
                    canvas.drawLine(480, 780, 480, 1220, myPaint);
                    canvas.drawLine(680, 780, 680, 1220, myPaint);
                    canvas.drawLine(880, 780, 880, 1220, myPaint);
                    canvas.drawLine(1030, 780, 1030, 1220, myPaint);

                    float total1 = 0, total2 = 0, total3 = 0, total4 = 0, total5 = 0;
                    if (item1Spinner.getSelectedItemPosition()!=0)
                    {

                        myPaint.setStyle(Paint.Style.STROKE);
                        myPaint.setStrokeWidth(2f);
                        canvas.drawRect(20, 860, pageWidth-20, 920, myPaint);

                        myPaint.setTextAlign(Paint.Align.LEFT);
                        myPaint.setStyle(Paint.Style.FILL);
                        canvas.drawText("1.", 40, 910, myPaint);
                        canvas.drawText("450", 110, 910, myPaint);
                        canvas.drawText(item1Spinner.getSelectedItem().toString(), 200, 910, myPaint);
                        canvas.drawText(String.valueOf(prices[item1Spinner.getSelectedItemPosition()]), 700, 910, myPaint);
                        canvas.drawText(qty1.getText().toString(), 900, 910, myPaint);
                        total1 = Float.parseFloat(qty1.getText().toString())*prices[item1Spinner.getSelectedItemPosition()];
                        myPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(total1), pageWidth-40, 910, myPaint);
                        myPaint.setTextAlign(Paint.Align.LEFT);

                        canvas.drawLine(480, 790, 480, 920, myPaint);
                        canvas.drawLine(880, 790, 880, 920, myPaint);
                        canvas.drawLine(180, 790, 180, 920, myPaint);
                        canvas.drawLine(100, 790, 100, 920, myPaint);
                        canvas.drawLine(1030, 790, 1030, 920, myPaint);

                    }
                    if (item2Spinner.getSelectedItemPosition()!=0) {

                        myPaint.setStyle(Paint.Style.STROKE);
                        myPaint.setStrokeWidth(2f);
                        canvas.drawRect(20, 920, pageWidth-20, 980, myPaint);


                        myPaint.setTextAlign(Paint.Align.LEFT);
                        myPaint.setStyle(Paint.Style.FILL);
                        canvas.drawText("2.", 40, 970, myPaint);
                        canvas.drawText("550", 110, 970, myPaint);
                        canvas.drawText(item2Spinner.getSelectedItem().toString(), 200, 970, myPaint);
                        canvas.drawText(String.valueOf(prices[item2Spinner.getSelectedItemPosition()]), 700, 970, myPaint);
                        canvas.drawText(qty2.getText().toString(), 900, 970, myPaint);
                        total2 = Float.parseFloat(qty2.getText().toString())*prices[item2Spinner.getSelectedItemPosition()];
                        myPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(total2), pageWidth-40, 970, myPaint);
                        myPaint.setTextAlign(Paint.Align.LEFT);

                        canvas.drawLine(180, 790, 180, 980, myPaint);
                        canvas.drawLine(100, 790, 100, 980, myPaint);
                        canvas.drawLine(680, 790, 680, 980, myPaint);
                        canvas.drawLine(880, 790, 880, 980, myPaint);
                        canvas.drawLine(1030, 790, 1030, 980, myPaint);

                    }

                    if (item3Spinner.getSelectedItemPosition()!=0)
                    {


                        myPaint.setStyle(Paint.Style.STROKE);
                        myPaint.setStrokeWidth(2f);
                        canvas.drawRect(20, 980, pageWidth-20, 1040, myPaint);


                        myPaint.setTextAlign(Paint.Align.LEFT);
                        myPaint.setStyle(Paint.Style.FILL);
                        canvas.drawText("3.", 40, 1030, myPaint);
                        canvas.drawText("350", 110, 1030, myPaint);
                        canvas.drawText(item3Spinner.getSelectedItem().toString(), 200, 1030, myPaint);
                        canvas.drawText(String.valueOf(prices[item3Spinner.getSelectedItemPosition()]), 700, 1030, myPaint);
                        canvas.drawText(qty3.getText().toString(), 900, 1030, myPaint);
                        total3 = Float.parseFloat(qty3.getText().toString())*prices[item3Spinner.getSelectedItemPosition()];
                        myPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(total3), pageWidth-40, 1030, myPaint);
                        myPaint.setTextAlign(Paint.Align.LEFT);


                        canvas.drawLine(100, 790, 100, 1040, myPaint);
                        canvas.drawLine(180, 790, 180, 1040, myPaint);
                        canvas.drawLine(680, 790, 680, 1040, myPaint);
                        canvas.drawLine(880, 790, 880, 1040, myPaint);
                        canvas.drawLine(1030, 790, 1030, 1040, myPaint);


                    }

                    if (item4Spinner.getSelectedItemPosition()!=0)
                    {


                        myPaint.setStyle(Paint.Style.STROKE);
                        myPaint.setStrokeWidth(2f);
                        canvas.drawRect(20, 1040, pageWidth-20, 1100, myPaint);


                        myPaint.setTextAlign(Paint.Align.LEFT);
                        myPaint.setStyle(Paint.Style.FILL);
                        canvas.drawText("4.", 40, 1090, myPaint);
                        canvas.drawText("550", 110, 1090, myPaint);
                        canvas.drawText(item4Spinner.getSelectedItem().toString(), 200, 1090, myPaint);
                        canvas.drawText(String.valueOf(prices[item4Spinner.getSelectedItemPosition()]), 700, 1090, myPaint);
                        canvas.drawText(qty4.getText().toString(), 900, 1090, myPaint);
                        total4 = Float.parseFloat(qty4.getText().toString())*prices[item4Spinner.getSelectedItemPosition()];
                        myPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(total4), pageWidth-40, 1090, myPaint);
                        myPaint.setTextAlign(Paint.Align.LEFT);


                        canvas.drawLine(180, 790, 180, 1100, myPaint);
                        canvas.drawLine(680, 790, 680, 1100, myPaint);
                        canvas.drawLine(880, 790, 880, 1100, myPaint);
                        canvas.drawLine(1030, 790, 1030, 1100, myPaint);


                    }

                    if (item5Spinner.getSelectedItemPosition()!=0)
                    {


                        myPaint.setStyle(Paint.Style.STROKE);
                        myPaint.setStrokeWidth(2f);
                        canvas.drawRect(20, 1100, pageWidth-20, 1160, myPaint);


                        myPaint.setTextAlign(Paint.Align.LEFT);
                        myPaint.setStyle(Paint.Style.FILL);
                        canvas.drawText("5.", 40, 1150, myPaint);
                        canvas.drawText("450", 110, 1150, myPaint);
                        canvas.drawText(item5Spinner.getSelectedItem().toString(), 200, 1150, myPaint);
                        canvas.drawText(String.valueOf(prices[item5Spinner.getSelectedItemPosition()]), 700, 1150, myPaint);
                        canvas.drawText(qty3.getText().toString(), 900, 1150, myPaint);
                        total5 = Float.parseFloat(qty3.getText().toString())*prices[item3Spinner.getSelectedItemPosition()];
                        myPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(total5), pageWidth-40, 1150, myPaint);
                        myPaint.setTextAlign(Paint.Align.LEFT);


                        canvas.drawLine(180, 790, 180, 1160, myPaint);
                        canvas.drawLine(680, 790, 680, 1160, myPaint);
                        canvas.drawLine(880, 790, 880, 1160, myPaint);
                        canvas.drawLine(1030, 790, 1030, 1160, myPaint);

                    }

                    myPaint.setStyle(Paint.Style.STROKE);
                    myPaint.setStrokeWidth(2f);
                    canvas.drawRect(20, 1160, pageWidth-20, 1220, myPaint);
                    canvas.drawRect(20, 1220, pageWidth-20, 1280, myPaint);
                    canvas.drawRect(20, 1280, pageWidth-20, 1340, myPaint);
                    canvas.drawRect(20, 1340, pageWidth-20, 1400, myPaint);
                    canvas.drawRect(20, 1400, pageWidth-20, 1460, myPaint);

                    float subtotal = total1 + total2 + total3 + total4 + total5;

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setStyle(Paint.Style.FILL);
                    canvas.drawText("Bank Name", 40, 1270, myPaint);
                    canvas.drawText("Account Number", 40, 1330, myPaint);
                    canvas.drawText("IFSC Code", 40, 1390, myPaint);
                    canvas.drawText("Brance Name", 40, 1450, myPaint);

                     //Taxable Amount
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subtotal), pageWidth-40, 1270, myPaint);
                    myPaint.setTextAlign(Paint.Align.LEFT);

                    //Discount Amount
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("0.0", pageWidth-40, 1330, myPaint);
                    myPaint.setTextAlign(Paint.Align.LEFT);

                    //Tax Amount
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subtotal*12/100), pageWidth-40, 1390, myPaint);
                    myPaint.setTextAlign(Paint.Align.LEFT);

                    //Bill Amount
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subtotal + (subtotal*12/100)), pageWidth-40, 1450, myPaint);
                    myPaint.setTextAlign(Paint.Align.LEFT);






                    canvas.drawText("SubTotal Amount", 700, 1270, myPaint);
                    canvas.drawText("Discount Amount", 700, 1330, myPaint);
                    canvas.drawText("Tax Amount(12%)", 700, 1390, myPaint);
                    canvas.drawText("Bill Amount", 700, 1450, myPaint);

                    canvas.drawLine(280, 1220, 280, 1460, myPaint);
                    canvas.drawLine(690, 1220, 690, 1460, myPaint);
                    canvas.drawLine(950, 1220, 950, 1460, myPaint);


                  /*  canvas.drawLine(680, 790, 680, 1400, myPaint);
                    canvas.drawLine(880, 790, 880, 1400, myPaint);
                    canvas.drawLine(1030, 790, 1030, 1400, myPaint);*/
                  /* canvas.drawLine(680, 1600, pageWidth-20, 1600, myPaint);
                    canvas.drawText("sub total", 700, 1650, myPaint);
                    canvas.drawText(":", 900, 1650, myPaint);
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subtotal), pageWidth-40, 1650, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("Tax (12%)", 700, 1700, myPaint);
                    canvas.drawText(":", 900,   1700, myPaint);
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subtotal*12/100), pageWidth-40, 1700, myPaint);
                    myPaint.setTextAlign(Paint.Align.LEFT);
*/
                    myPaint.setColor(Color.rgb(247, 147, 30));
                    canvas.drawRect(680, 1500, pageWidth-20, 1600, myPaint);

                    myPaint.setColor(Color.BLACK);
                    myPaint.setTextSize(50f);
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("Total :", 700, 1570, myPaint);
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subtotal + (subtotal*12/100)), pageWidth-40, 1570, myPaint);

                    myPdfDocument.finishPage(myPage1);

                    File file = new File(Environment.getExternalStorageDirectory(), "/Hello.pdf");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setType("application/pdf");
                    startActivityForResult(intent, 200);
                    Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath().toString());
                    chooser.addCategory(Intent.CATEGORY_OPENABLE);
                    chooser.setDataAndType(uri, "*/*");
                    try {
                        myPdfDocument.writeTo(new FileOutputStream(file));
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdfDocument.close();
                }
            }

        });
    }



}

