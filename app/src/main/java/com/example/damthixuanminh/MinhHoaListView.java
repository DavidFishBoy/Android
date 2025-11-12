package com.example.damthixuanminh;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // QUAN TRỌNG: Thêm import này

import java.util.ArrayList;

public class MinhHoaListView extends AppCompatActivity {

    ListView listView;
    ArrayList<String> originalData = new ArrayList<>(); // Danh sách dữ liệu gốc
    ArrayAdapter<String> arrayAdapter; // Chỉ một Adapter duy nhất

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minhhoalistview);

        // --- Kích hoạt Toolbar ---
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // -------------------------

        // --- Thêm dữ liệu ban đầu ---
        originalData.add("Bánh Mì");
        originalData.add("Phô mai");
        originalData.add("Trứng");
        originalData.add("Bánh Quy");
        originalData.add("Whey");
        originalData.add("Bún");
        originalData.add("Phở");
        originalData.add("Cơm");
        originalData.add("Bánh Đúc");
        originalData.add("Khoai");
        originalData.add("Ngô");
        originalData.add("Sắn");
        // -------------------------

        listView = (ListView) findViewById(R.id.lvproduct);
        // Khởi tạo Adapter với một bản sao của dữ liệu gốc để có thể lọc
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(originalData));
        listView.setAdapter(arrayAdapter);

        // Sự kiện nhấn giữ để xóa
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String itemToRemove = arrayAdapter.getItem(position);

                // Hiển thị hộp thoại xác nhận trước khi xóa
                new AlertDialog.Builder(MinhHoaListView.this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa sản phẩm '" + itemToRemove + "' không?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Xóa khỏi adapter và danh sách gốc
                                arrayAdapter.remove(itemToRemove);
                                originalData.remove(itemToRemove);
                                Toast.makeText(MinhHoaListView.this, "Đã xóa: " + itemToRemove, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", null) // Không làm gì khi nhấn Hủy
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return true; // Đánh dấu sự kiện đã được xử lý
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoption_add_exit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.thoatungdung) {
            finish();
            return true;
        }

        if (id == R.id.call) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0942160880"));
            startActivity(intent);
            return true;
        }

        if (id == R.id.addpro) {
            final EditText input = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Thêm sản phẩm mới")
                    .setView(input)
                    .setPositiveButton("Thêm", (dialog, which) -> {
                        String newProduct = input.getText().toString().trim();
                        if (!newProduct.isEmpty()) {
                            originalData.add(newProduct); // Thêm vào danh sách gốc
                            arrayAdapter.add(newProduct);  // Thêm vào adapter để hiển thị
                            Toast.makeText(this, "Đã thêm: " + newProduct, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        }

        if (id == R.id.truyendata) {
            Intent intent = new Intent(this, MainActivity.class);
            Bundle b = new Bundle();
            b.putString("Ten", "Mỹ Anh");
            b.putInt("Tuoi", 23);
            intent.putExtra("du lieu", b);
            startActivity(intent);
            return true;
        }

        if (id == R.id.suapro) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            int padding = (int) (16 * getResources().getDisplayMetrics().density);
            layout.setPadding(padding, padding, padding, padding);

            final EditText oldNameInput = new EditText(this);
            oldNameInput.setHint("Nhập tên sản phẩm cần sửa");
            layout.addView(oldNameInput);

            final EditText newNameInput = new EditText(this);
            newNameInput.setHint("Nhập tên sản phẩm mới");
            layout.addView(newNameInput);

            new AlertDialog.Builder(this)
                    .setTitle("Sửa sản phẩm")
                    .setView(layout)
                    .setPositiveButton("Sửa", (dialog, which) -> {
                        String oldName = oldNameInput.getText().toString().trim();
                        String newName = newNameInput.getText().toString().trim();
                        int index = originalData.indexOf(oldName);

                        if (index != -1 && !newName.isEmpty()) {
                            originalData.set(index, newName); // Cập nhật trong danh sách gốc
                            // Cập nhật lại adapter để hiển thị thay đổi
                            arrayAdapter.clear();
                            arrayAdapter.addAll(originalData);
                            Toast.makeText(this, "Đã sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Không tìm thấy sản phẩm hoặc tên mới rỗng", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        }

        if (id == R.id.search) {
            final EditText input = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Tìm kiếm sản phẩm")
                    .setView(input)
                    .setPositiveButton("Tìm", (dialog, which) -> {
                        String keyword = input.getText().toString().toLowerCase().trim();
                        ArrayList<String> filteredResults = new ArrayList<>();
                        for (String product : originalData) {
                            if (product.toLowerCase().contains(keyword)) {
                                filteredResults.add(product);
                            }
                        }
                        arrayAdapter.clear();
                        arrayAdapter.addAll(filteredResults);
                        if (filteredResults.isEmpty()) {
                            Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        }

        if (id == R.id.showall) {
            arrayAdapter.clear();
            arrayAdapter.addAll(originalData); // Tải lại toàn bộ dữ liệu từ danh sách gốc
            Toast.makeText(this, "Đã hiển thị tất cả sản phẩm", Toast.LENGTH_SHORT).show();
            return true;
        }

        // Không cần xử lý xoá ở đây vì đã có nhấn giữ (Long Click)
        // if (id == R.id.xoapro) { ... }

        return super.onOptionsItemSelected(item);
    }
}
