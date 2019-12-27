package com.kotlin.freaking.generators;


import com.kotlin.freak_annotation.annotations.EntryGenerator;
import com.kotlin.freak_core.wechat.templates.WXEntryTemplates;

@EntryGenerator(packageName = "com.kotlin.freaking", entryTemplete = WXEntryTemplates.class)
public interface WechatEntry {
}
