import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CheckImageMappingTask : DefaultTask() {

    @get:Input
    abstract val folderWithInfoJsonFiles: Property<File>

    @get:Input
    abstract val infoPageUtilFile: Property<File>

    @TaskAction
    fun checkImageMap() {
        val infoJsonFiles = folderWithInfoJsonFiles.get().listFiles { _, name -> name.endsWith("_info.json") }
        val mapFunctionFile = infoPageUtilFile.get()
        val imagesInJsonFiles = mutableSetOf<String>()
        val imagesInFunction = mutableSetOf<String>()
        infoJsonFiles.forEach { file ->
            file.useLines { lines ->
                lines.filter { it.contains("\"imageid\"") }.forEach {
                    it.split(":").forEach { line ->
                        val existingStringRes = line.replace("\"imageid\"", "").replace(",", "").replace("\"", "").trim()
                        if (existingStringRes.isNotBlank()) {
                            imagesInJsonFiles.add(existingStringRes)
                        }
                    }
                }
            }
        }
        mapFunctionFile.useLines { lines ->
            val images = lines
                .filter { it.contains("->") && !it.contains("else") }
                .map { it.split("->")[0].replace(",", "").replace("\"", "").trim() }
                .toList()
            imagesInFunction.addAll(images)
        }

        if (imagesInFunction.size < imagesInJsonFiles.size) {
            val unmappedImages = mutableSetOf<String>()
            unmappedImages.addAll(imagesInJsonFiles)
            unmappedImages.removeAll(imagesInFunction)
            println("Not all images are mapped by function. The following Images are not mapped: ${unmappedImages.joinToString()}")
            println("From Json Files   : ${imagesInJsonFiles.sorted().joinToString()}")
            println("From Mapped values: ${imagesInFunction.sorted().joinToString()}")
        } else {
            println("All images should be visible in the App")
            println("${imagesInJsonFiles.size} Images are provided in json files")
            println("${imagesInFunction.size} Images are covered by the InfoPageUtil function")
        }
    }

}
