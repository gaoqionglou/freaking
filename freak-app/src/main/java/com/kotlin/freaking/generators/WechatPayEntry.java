package com.kotlin.freaking.generators;


import com.kotlin.freak_annotation.annotations.PayEntryGenerator;
import com.kotlin.freak_core.wechat.templates.WXPayEntryTemplates;

@PayEntryGenerator(packageName = "com.kotlin.freaking", payEntryTemplete = WXPayEntryTemplates.class)
public interface WechatPayEntry {
}
