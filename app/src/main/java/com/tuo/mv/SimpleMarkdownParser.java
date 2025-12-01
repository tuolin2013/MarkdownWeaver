package com.tuo.mv;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SimpleMarkdownParser {

    public static String parse(String markdown) {
        if (markdown == null || markdown.isEmpty()) return "";

        List<Extension> extensions = Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                AutolinkExtension.create()
        );

        Parser parser = Parser.builder().extensions(extensions).build();

        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                // 关键：注册样式注入器
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new WeChatStyleProvider();
                    }
                })
                .build();

        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    /**
     * 核心修复类：WeChatStyleProvider
     * 作用：将 CSS 直接写死在 HTML 标签的 style 属性里 (Inline Styles)
     * 只有这样，微信编辑器才不会把样式过滤掉。
     */
    private static class WeChatStyleProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            
            // 1. 图片自适应 (防止撑破屏幕)
            if (node instanceof Image) {
                attributes.put("style", "max-width: 100%; height: auto; border-radius: 6px; display: block; margin: 10px auto; box-shadow: 0 2px 4px rgba(0,0,0,0.1);");
            }

            // 2. 表格整体样式 (Table)
            if (tagName.equals("table")) {
                attributes.put("style", 
                    "width: 100%; " +
                    "border-collapse: collapse; " +
                    "border-spacing: 0; " +
                    "margin-bottom: 16px; " +
                    "font-size: 14px; " +
                    "line-height: 1.6; " +
                    "color: #333333;" // 确保文字颜色
                );
            }

            // 3. 表头样式 (TH)
            if (tagName.equals("th")) {
                attributes.put("style", 
                    "background-color: #f2f2f2; " + // 浅灰背景
                    "font-weight: bold; " +
                    "text-align: left; " +
                    "padding: 8px 12px; " +
                    "border: 1px solid #d0d0d0; " + // 强制显示边框
                    "min-width: 60px;" // 防止太窄
                );
            }

            // 4. 单元格样式 (TD)
            if (tagName.equals("td")) {
                attributes.put("style", 
                    "padding: 8px 12px; " +
                    "border: 1px solid #d0d0d0; " + // 强制显示边框
                    "vertical-align: top; " +
                    "word-break: break-all;" // 防止长英文撑破
                );
            }
            
            // 5. 引用块 (Blockquote) - 很多时候微信也会丢引用样式
            if (tagName.equals("blockquote")) {
                attributes.put("style", 
                    "border-left: 4px solid #4caf50; " + // 绿色左边框
                    "background-color: #f9f9f9; " +
                    "color: #555; " +
                    "padding: 10px 16px; " +
                    "margin: 16px 0;"
                );
            }
        }
    }
}