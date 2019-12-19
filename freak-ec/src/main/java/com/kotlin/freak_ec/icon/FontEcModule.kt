import com.joanzapata.iconify.Icon
import com.joanzapata.iconify.IconFontDescriptor
import com.kotlin.freak_ec.icon.EcIcons

class FontEcModule : IconFontDescriptor {
    override fun ttfFileName(): String {

        return "iconfont.ttf"
    }

    @Suppress("UNCHECKED_CAST")
    override fun characters(): Array<Icon> {
        return EcIcons.values() as Array<Icon>
    }

}