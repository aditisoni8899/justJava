package io.github.aditisoni8899.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int numberOfCoffees=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void submitOrder(View view) {
        EditText nameField=(EditText) findViewById(R.id.name_field);
        String name=nameField.getText().toString();

        //displayPrice(numberOfCoffees * 5);
        CheckBox whippedCream=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream= whippedCream.isChecked();
        CheckBox chocolate=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate= chocolate.isChecked();
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(price,hasWhippedCream,hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For "+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(priceMessage);
    }

    public String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String addName)
    {   String choco;
        if (addChocolate)
           choco="Yes";
        else
          choco="No";
        String cream;
        if (addWhippedCream)
            cream="Yes";
        else
            cream="No";
        String summary="Name: "+addName+"\nQuantity: "+ numberOfCoffees +"\nAdd whipped cream? "+cream+"\nAdd Chocolate? "+choco+ "\nTotal: \u20B9"+ price+"\nThank You!";
        return summary;
    }

    public void increment(View view) {
        if (numberOfCoffees==100)
        {
            Toast.makeText(this,"You cannot have more than 100 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees=numberOfCoffees+1;
        display(numberOfCoffees);

    }

     private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
         int basePrice=5;
         if (addWhippedCream){
             basePrice=basePrice + 1 ;
         }
         if (addChocolate)
         {
             basePrice=basePrice +2;
         }
        int price= numberOfCoffees *  basePrice;
         return price;
     }

    public void decrement(View view) {
        if(numberOfCoffees==1)
        {
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees=numberOfCoffees-1;
        display(numberOfCoffees);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}
