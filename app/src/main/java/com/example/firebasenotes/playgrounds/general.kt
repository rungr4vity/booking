import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Document
import org.w3c.dom.Element

fun main() {
    val file = File("app/src/main/java/com/example/firebasenotes/playgrounds/F0000003257.xml")
    val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    val document: Document = documentBuilder.parse(file)

    // Normalize XML structure
    document.documentElement.normalize()

    // Read root element
    val root: Element = document.documentElement
    println("Root element: ${root.nodeName}")

    // Read Emisor
    val emisor = root.getElementsByTagName("cfdi:Emisor").item(0) as Element
    println("Emisor Rfc: ${emisor.getAttribute("Rfc")}")
    println("Emisor Nombre: ${emisor.getAttribute("Nombre")}")

    // Read Receptor
    val receptor = root.getElementsByTagName("cfdi:Receptor").item(0) as Element
    println("Receptor Rfc: ${receptor.getAttribute("Rfc")}")
    println("Receptor Nombre: ${receptor.getAttribute("Nombre")}")

    val impuestos = root.getElementsByTagName("cfdi:Impuestos")
    for(x in 0 until impuestos.length) {
        val impuesto = impuestos.item(x) as Element
        println("Impuestos TotalImpuestosTrasladados: ${impuesto.getAttributeNode("TotalImpuestosTrasladados")}")
    }

    // Read Conceptos
    val conceptos = root.getElementsByTagName("cfdi:Concepto")
    for (i in 0 until conceptos.length) {
        val concepto = conceptos.item(i) as Element
        println("Concepto Cantidad: ${concepto.getAttribute("Cantidad")}")
        println("Concepto Descripcion: ${concepto.getAttribute("Descripcion")}")
        println("Concepto ValorUnitario: ${concepto.getAttribute("ValorUnitario")}")
        println("Concepto Importe: ${concepto.getAttribute("Importe")}")
    }
}