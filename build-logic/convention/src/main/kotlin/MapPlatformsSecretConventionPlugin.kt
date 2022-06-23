import org.gradle.api.Plugin
import org.gradle.api.Project

class MapPlatformsSecretConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager){
                apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
            }
        }
    }
}