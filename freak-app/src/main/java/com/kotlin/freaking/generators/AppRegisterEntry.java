package com.kotlin.freaking.generators;


import com.kotlin.freak_annotation.annotations.AppRegisterGenerator;
import com.kotlin.freak_core.wechat.templates.AppRegisterTemplates;

@AppRegisterGenerator(packageName = "com.kotlin.freaking", registerTemplate = AppRegisterTemplates.class)
public interface AppRegisterEntry {
}
