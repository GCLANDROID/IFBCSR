package io.cordova.ifb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.cordova.ifb.R;
import io.cordova.ifb.adapter.CategoryAdapter;
import io.cordova.ifb.adapter.NewComDisplayAdapter;
import io.cordova.ifb.databinding.ActivityNewCompetitorDisplayMatrixBinding;
import io.cordova.ifb.module.BrandModel;
import io.cordova.ifb.module.NewDetailsProductModel;
import io.cordova.ifb.module.NewDisplayMatrixProModel;
import io.cordova.ifb.module.UnitModel;

public class NewCompetitorDisplayMatrixActivity extends AppCompatActivity {
    ActivityNewCompetitorDisplayMatrixBinding binding;
    RecyclerView recyclerCategory;
    CategoryAdapter adapter;
    List<Category> categoryList;
    Button btnSave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewCompetitorDisplayMatrixBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //initView();
        btnSave = findViewById(R.id.btnSave);
        recyclerCategory = findViewById(R.id.rvRecyclerView);

        // Layout Manager
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this));

        // Data
        categoryList = new ArrayList<>();
        loadData();
        Log.e("list", "onCreate: "+categoryList.size());
        // Adapter
        adapter = new CategoryAdapter(categoryList);
        recyclerCategory.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray finalArray = new JSONArray();

                try {

                    for (Category category : categoryList) {

                        JSONObject categoryObj = new JSONObject();

                        categoryObj.put("Category", category.categoryName);

                        JSONObject brandObj = new JSONObject();
                        int grandTotal = 0;

                        // Loop brands (rows)
                        for (BrandRow brand : category.brands) {

                            int sum = 0;

                            for (int val : brand.values) {
                                sum += val;
                            }

                            grandTotal += sum;

                            brandObj.put(brand.brandName, String.valueOf(sum));
                        }

                        JSONArray brandArray = new JSONArray();
                        brandArray.put(brandObj);

                        categoryObj.put("brand_list", brandArray);
                        categoryObj.put("total_mount", String.valueOf(grandTotal));

                        finalArray.put(categoryObj);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("FINAL_JSON", finalArray.toString());
            }
        });
    }

    private void loadData() {
        categoryList = new ArrayList<>();

        Category cat1 = new Category();
        cat1.categoryName = "Semi-Automatic Top Loading";

        // 🔥 Capacities (columns now)
        cat1.capacities = Arrays.asList("6 Kg", "10 Kg", "15 Kg");

        // 🔥 Brand rows
        List<BrandRow> brandRows = new ArrayList<>();

        // Samsung
        BrandRow b1 = new BrandRow();
        b1.brandName = "Samsung";
        b1.values = new ArrayList<>(Arrays.asList(3, 4, 4));
        brandRows.add(b1);

        // LG
        BrandRow b2 = new BrandRow();
        b2.brandName = "LG";
        b2.values = new ArrayList<>(Arrays.asList(3, 3, 3));
        brandRows.add(b2);

        // Bosch
        BrandRow b3 = new BrandRow();
        b3.brandName = "Bosch";
        b3.values = new ArrayList<>(Arrays.asList(5, 6, 6));
        brandRows.add(b3);

        // Haier
        BrandRow b4 = new BrandRow();
        b4.brandName = "Haier";
        b4.values = new ArrayList<>(Arrays.asList(6, 0, 0));
        brandRows.add(b4);

        // Voltas
        BrandRow b5 = new BrandRow();
        b5.brandName = "Voltas";
        b5.values = new ArrayList<>(Arrays.asList(2, 6, 6));
        brandRows.add(b5);

        // IFB
        BrandRow b6 = new BrandRow();
        b6.brandName = "IFB";
        b6.values = new ArrayList<>(Arrays.asList(9, 9, 30));
        brandRows.add(b6);

        cat1.brands = brandRows;

        // Add multiple categories (demo)
        categoryList.add(cat1);
        categoryList.add(cat1);
        categoryList.add(cat1);
        categoryList.add(cat1);
        categoryList.add(cat1);

    }

    /*private void initView() {
        *//*ArrayList<BrandModel> newBrandModel = new ArrayList<>();
        newBrandModel.add(new BrandModel("SAMSUNG","H"));
        newBrandModel.add(new BrandModel("LG","H"));
        newBrandModel.add(new BrandModel("BOSCH","H"));

        ArrayList<NewDetailsProductModel> newDetailsProductModels = new ArrayList<>();
        newDetailsProductModels.add(new NewDetailsProductModel("CAPACITY",newBrandModel,"TOTAL","H"));

        ArrayList<BrandModel> newBrandModel1 = new ArrayList<>();
        newBrandModel.add(new BrandModel("SAMSUNG","I"));
        newBrandModel.add(new BrandModel("LG","I"));
        newBrandModel.add(new BrandModel("BOSCH","I"));
        newDetailsProductModels.add(new NewDetailsProductModel("6 KG",newBrandModel1,"0","I"));
        ArrayList<BrandModel> newBrandModel2 = new ArrayList<>();
        newBrandModel.add(new BrandModel("SAMSUNG","I"));
        newBrandModel.add(new BrandModel("LG","I"));
        newBrandModel.add(new BrandModel("BOSCH","I"));
        newDetailsProductModels.add(new NewDetailsProductModel("10 KG",newBrandModel2,"0","I"));

        ArrayList<NewDisplayMatrixProModel> newModelList = new ArrayList<>();
        newModelList.add(new NewDisplayMatrixProModel("Semi-Automatic Top Loading", newDetailsProductModels));

        binding.rvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newComDisplayAdapter = new NewComDisplayAdapter(this,newModelList);
        binding.rvRecyclerView.setAdapter(newComDisplayAdapter);*//*

        ArrayList<UnitModel> newUnitArray = new ArrayList<>();
        newUnitArray.add(new UnitModel("6 KG","H"));
        newUnitArray.add(new UnitModel("12 KG","H"));

        ArrayList<UnitModel> newUnitArray1 = new ArrayList<>();
        newUnitArray.add(new UnitModel("6 KG","T"));
        newUnitArray.add(new UnitModel("12 KG","T"));

        ArrayList<NewDetailsProductModel> newDetailsProductModels = new ArrayList<>();
        newDetailsProductModels.add(new NewDetailsProductModel("Brand",newUnitArray,"Total","H"));
        newDetailsProductModels.add(new NewDetailsProductModel("SAMSUNG",newUnitArray1,"Total","T"));
        newDetailsProductModels.add(new NewDetailsProductModel("LG",newUnitArray1,"Total","T"));
        newDetailsProductModels.add(new NewDetailsProductModel("BOSCH",newUnitArray1,"Total","T"));

        ArrayList<NewDisplayMatrixProModel> newModelList = new ArrayList<>();
        newModelList.add(new NewDisplayMatrixProModel("Semi-Automatic Top Loading", newDetailsProductModels));

        binding.rvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newComDisplayAdapter = new NewComDisplayAdapter(this,newModelList);
        binding.rvRecyclerView.setAdapter(newComDisplayAdapter);
    }*/


    public class Category {
        public String categoryName;
        public List<String> capacities; // instead of rows
        public List<BrandRow> brands;
    }

    public class BrandRow {
        public String brandName;
        public List<Integer> values; // size = capacities.size()
    }

}
