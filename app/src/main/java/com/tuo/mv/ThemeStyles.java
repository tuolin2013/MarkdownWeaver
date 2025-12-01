package com.tuo.mv;

public class ThemeStyles {
    
    private static final String COMMON_TABLE = 
        "table { width: 100%; border-collapse: collapse; margin: 16px 0; font-size: 14px; }" +
        "th { background-color: #f8f9fa; font-weight: bold; text-align: left; border: 1px solid #dfe2e5; padding: 6px 13px; }" +
        "td { border: 1px solid #dfe2e5; padding: 6px 13px; line-height: 1.5; }";

    // Next
    public static final String STYLE_NEXT = 
        "body { font-family: 'Lato', sans-serif; line-height: 1.8; color: #333; padding: 10px; }" +
        "h1 { text-align: center; font-size: 22px; font-weight: bold; margin: 20px 0; border-bottom: 1px solid #eee; padding-bottom: 10px; }" +
        "h2 { font-size: 18px; font-weight: bold; border-left: 4px solid #fd7e14; padding-left: 10px; margin: 30px 0 15px; background: #f8f9fa; }" +
        "h3 { font-size: 16px; font-weight: bold; margin: 20px 0 10px; }" +
        "p { margin: 15px 0; text-align: justify; }" +
        "code { background: #f0f0f0; padding: 2px 4px; border-radius: 3px; color: #c7254e; font-family: monospace; }" +
        "blockquote { border-left: 4px solid #ddd; color: #666; padding-left: 15px; margin: 15px 0; background: #f9f9f9; padding: 10px; }" +
        COMMON_TABLE;

    // Hexo
    public static final String STYLE_HEXO = 
        "body { font-family: 'Georgia', serif; line-height: 2.0; color: #333; background-color: #f5f7f9; padding: 15px; }" +
        "h1 { text-align: center; font-size: 24px; margin-bottom: 30px; color: #000; }" +
        "h2 { font-size: 20px; text-align: center; margin-top: 40px; margin-bottom: 20px; border-bottom: 1px solid #ccc; padding-bottom: 10px; }" +
        "code { background: #e3f2fd; color: #1565c0; padding: 2px 5px; border-radius: 2px; }" +
        "blockquote { background: #fff; border-left: 5px solid #2196f3; padding: 15px; }" +
        COMMON_TABLE;

    // GitHub
    public static final String STYLE_GITHUB = 
        "body { font-family: -apple-system, sans-serif; line-height: 1.6; color: #24292e; padding: 10px; }" +
        "h1, h2 { border-bottom: 1px solid #eaecef; padding-bottom: .3em; margin-bottom: 16px; }" +
        "code { background-color: rgba(27,31,35,.05); border-radius: 3px; padding: .2em .4em; }" +
        "blockquote { color: #6a737d; border-left: .25em solid #dfe2e5; padding: 0 1em; }" +
        COMMON_TABLE;

    // WeChat
    public static final String STYLE_WECHAT = 
        "body { font-family: sans-serif; line-height: 1.8; color: #3f3f3f; font-size: 16px; padding: 10px; }" +
        "h1 { font-size: 20px; color: #00bc12; text-align: center; margin: 20px 0; }" +
        "h2 { font-size: 18px; color: #fff; background: #00bc12; display: inline-block; padding: 5px 15px; border-radius: 0 20px 20px 0; margin: 30px 0 15px; }" +
        "code { background: #f2f9f1; color: #00bc12; padding: 2px 5px; border-radius: 3px; }" +
        "blockquote { background: #f6f6f6; padding: 15px; border-radius: 5px; color: #888; }" +
        COMMON_TABLE;

    // --- 新增: Google Material Style ---
    public static final String STYLE_GOOGLE = 
        "body { font-family: 'Roboto', 'Product Sans', sans-serif; line-height: 1.6; color: #202124; padding: 10px; }" +
        "h1 { font-size: 24px; color: #1a73e8; margin-top: 24px; margin-bottom: 12px; font-weight: 400; }" + // Google Blue
        "h2 { font-size: 20px; color: #202124; margin-top: 20px; margin-bottom: 10px; font-weight: 500; }" +
        "a { color: #1a73e8; text-decoration: none; }" +
        "code { background-color: #f1f3f4; color: #d93025; padding: 2px 4px; border-radius: 4px; font-family: 'Roboto Mono', monospace; }" + // Google Red accent
        "blockquote { border-left: 4px solid #1a73e8; padding-left: 16px; color: #5f6368; margin: 16px 0; }" +
        COMMON_TABLE.replace("#f8f9fa", "#e8f0fe").replace("#dfe2e5", "#dadce0"); // 轻微调整表格颜色

    // --- 新增: Apple Developer Style ---
    public static final String STYLE_APPLE = 
        "body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; line-height: 1.6; color: #1d1d1f; padding: 10px; letter-spacing: -0.01em; }" +
        "h1 { font-size: 24px; font-weight: 700; margin-top: 24px; margin-bottom: 16px; color: #000; letter-spacing: -0.02em; }" +
        "h2 { font-size: 20px; font-weight: 600; margin-top: 20px; margin-bottom: 12px; color: #1d1d1f; }" +
        "a { color: #0066cc; text-decoration: none; hover: underline; }" + // Apple Blue
        "code { font-family: 'SF Mono', Menlo, monospace; font-size: 0.9em; background-color: #f5f5f7; padding: 0.2em 0.4em; border-radius: 4px; color: #333; border: 1px solid #d2d2d7; }" +
        "blockquote { border-left: 4px solid #d2d2d7; padding-left: 16px; color: #86868b; margin: 16px 0; font-style: italic; }" +
        COMMON_TABLE.replace("#f8f9fa", "#f5f5f7").replace("#dfe2e5", "#d2d2d7");
}