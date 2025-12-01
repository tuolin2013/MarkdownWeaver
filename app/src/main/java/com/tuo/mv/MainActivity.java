package com.tuo.mv;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    
    private EditText etMarkdown;
    private WebView webViewPreview;
    private RadioGroup rgThemes;
    private TextView btnCopy;
    private ImageButton btnMenu;
    
    private ImageButton btnModeEdit;
    private ImageButton btnModePreview;
    private LinearLayout layoutThemeBar;
    
    private LinearLayout navNewDoc;
    private LinearLayout navDonate;
    private LinearLayout navAbout;

    private String currentCss = "";
    private String rawHtmlContent = "";
    private boolean isEditMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTransparent();
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();
        
        currentCss = ThemeStyles.STYLE_NEXT;
        etMarkdown.setText(MarkdownData.WELCOME_MESSAGE);
        
        switchToEditMode();
    }
    
    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(flags);
        }
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(Color.WHITE);
        
        etMarkdown = findViewById(R.id.et_markdown);
        webViewPreview = findViewById(R.id.webview_preview);
        rgThemes = findViewById(R.id.rg_themes);
        btnCopy = findViewById(R.id.btn_copy);
        btnMenu = findViewById(R.id.btn_menu);
        
        btnModeEdit = findViewById(R.id.btn_mode_edit);
        btnModePreview = findViewById(R.id.btn_mode_preview);
        layoutThemeBar = findViewById(R.id.layout_theme_bar);
        
        navNewDoc = findViewById(R.id.nav_new_doc);
        navDonate = findViewById(R.id.nav_donate);
        navAbout = findViewById(R.id.nav_about);
        
        webViewPreview.getSettings().setJavaScriptEnabled(false);
        webViewPreview.getSettings().setDefaultTextEncodingName("UTF-8");
    }

    private void setupListeners() {
        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navNewDoc.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            etMarkdown.setText("");
            switchToEditMode();
            Toast.makeText(this, "已新建文档", Toast.LENGTH_SHORT).show();
        });
        
        navDonate.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            showDonateDialog();
        });
        
        navAbout.setOnClickListener(v -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            showAboutDialog();
        });

        btnModeEdit.setOnClickListener(v -> switchToEditMode());
        btnModePreview.setOnClickListener(v -> switchToPreviewMode());

        rgThemes.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_next) currentCss = ThemeStyles.STYLE_NEXT;
            else if (checkedId == R.id.rb_hexo) currentCss = ThemeStyles.STYLE_HEXO;
            else if (checkedId == R.id.rb_github) currentCss = ThemeStyles.STYLE_GITHUB;
            else if (checkedId == R.id.rb_google) currentCss = ThemeStyles.STYLE_GOOGLE; // 新增 Google
            else if (checkedId == R.id.rb_apple) currentCss = ThemeStyles.STYLE_APPLE;   // 新增 Apple
            else if (checkedId == R.id.rb_wechat) currentCss = ThemeStyles.STYLE_WECHAT;
            
            if (!isEditMode) renderPreview(etMarkdown.getText().toString());
        });

        btnCopy.setOnClickListener(v -> copyToClipboard());
    }
    
    private void switchToEditMode() {
        isEditMode = true;
        etMarkdown.setVisibility(View.VISIBLE);
        webViewPreview.setVisibility(View.GONE);
        layoutThemeBar.setVisibility(View.GONE);
        btnCopy.setVisibility(View.GONE);
        
        btnModeEdit.setBackgroundResource(R.drawable.bg_toggle_item_selected);
        btnModeEdit.setImageTintList(ColorStateList.valueOf(Color.parseColor("#0B57D0")));
        
        btnModePreview.setBackgroundResource(android.R.color.transparent);
        btnModePreview.setImageTintList(ColorStateList.valueOf(Color.parseColor("#444746")));
    }
    
    private void switchToPreviewMode() {
        isEditMode = false;
        renderPreview(etMarkdown.getText().toString());
        
        etMarkdown.setVisibility(View.GONE);
        webViewPreview.setVisibility(View.VISIBLE);
        layoutThemeBar.setVisibility(View.VISIBLE);
        btnCopy.setVisibility(View.VISIBLE);
        
        btnModePreview.setBackgroundResource(R.drawable.bg_toggle_item_selected);
        btnModePreview.setImageTintList(ColorStateList.valueOf(Color.parseColor("#0B57D0")));
        
        btnModeEdit.setBackgroundResource(android.R.color.transparent);
        btnModeEdit.setImageTintList(ColorStateList.valueOf(Color.parseColor("#444746")));
    }

    private void renderPreview(String markdown) {
        rawHtmlContent = SimpleMarkdownParser.parse(markdown);
        String completeHtml = "<!DOCTYPE html><html><head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<style>" + currentCss + "</style>" +
                "</head><body>" +
                rawHtmlContent +
                "</body></html>";
        webViewPreview.loadDataWithBaseURL(null, completeHtml, "text/html", "utf-8", null);
    }
    
    private void copyToClipboard() {
        if (rawHtmlContent.isEmpty()) return;
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String htmlToCopy = "<div><style>" + currentCss + "</style>" + rawHtmlContent + "</div>";
        ClipData clip = ClipData.newHtmlText("MarkdownWeaver Content", etMarkdown.getText().toString(), htmlToCopy);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, R.string.msg_copy_success, Toast.LENGTH_SHORT).show();
    }
    
    private void showAboutDialog() {
         new AlertDialog.Builder(this)
                .setTitle(getString(R.string.about_title))
                .setMessage(getString(R.string.about_desc) + "\n\n" + getString(R.string.about_dev_info))
                .setPositiveButton(R.string.btn_close, null)
                .show();
    }

    private void showDonateDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_donate, null);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.donate_dialog_title))
                .setView(dialogView)
                .setPositiveButton(R.string.btn_thanks, null)
                .show();
    }
}