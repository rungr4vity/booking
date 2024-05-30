


package com.example.firebasenotes.bill

import com.example.firebasenotes.models.GastoDTO
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

interface toXml {
    companion object {

        fun toReadXML(path: String,theXML: String,
                      idUsuario: String,
                      idViaje:String,
                      imagen: String,
                      importe: String,
                      pdf: String,xml: String,comentario: String,descripcion: String,total: String): GastoDTO {

            //val filePath = path
            //val xmlFile = File(path)


            var gastoDTO = GastoDTO()

            val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document: Document = documentBuilder.parse(theXML.byteInputStream())

            // Normalize XML structure
            document.documentElement.normalize()

            // Read root element
            // root = compobante
            val root: Element = document.documentElement
            println("Root element: ${root.nodeName}")


            //val comprobante = root.getElementsByTagName("cfdi:Comprobante").item(0) as Element
            gastoDTO.fecha = root.getAttribute("Fecha").toString()
            gastoDTO.folio = root.getAttribute("Folio").toString()
            gastoDTO.formaPago = root.getAttribute("FormaPago").toInt()
            gastoDTO.lugarExpedicion = root.getAttribute("LugarExpedicion").toString()
            gastoDTO.metodoPago = root.getAttribute("MetodoPago").toString()
            gastoDTO.lugarExpedicion = root.getAttribute("LugarExpedicion").toString()
            gastoDTO.moneda = root.getAttribute("Moneda").toString()
            gastoDTO.subTotal = root.getAttribute("SubTotal").toString().toDouble()
            gastoDTO.total = root.getAttribute("Total").toString().toString()

            val concepto = root.getElementsByTagName("cfdi:Concepto").item(0) as Element
            gastoDTO.claveProdServ = concepto.getAttribute("ClaveProdServ").toInt()
            gastoDTO.importe = concepto.getAttribute("Importe").toString()

            gastoDTO.comentario = comentario
            gastoDTO.descripcion = descripcion
            gastoDTO.pdf = pdf
            gastoDTO.total = total
            gastoDTO.xml = xml
            gastoDTO.idUsuario = idUsuario
            gastoDTO.idViaje = idViaje
            gastoDTO.imagen = imagen
            gastoDTO.importe = importe
            gastoDTO.esDeducible = true


            // Read Emisor
            val emisor = root.getElementsByTagName("cfdi:Emisor").item(0) as Element
            gastoDTO.emisorNombre = emisor.getAttribute("Nombre")

            // Read Receptor
            val receptor = root.getElementsByTagName("cfdi:Receptor").item(0) as Element
            gastoDTO.emisorRegimen = receptor.getAttribute("RegimenFiscalReceptor")
            gastoDTO.emisorRfc = receptor.getAttribute("Rfc")


            gastoDTO.esDeducible = true


            val impuestos = root.getElementsByTagName("cfdi:Impuestos")
            for(x in 0 until impuestos.length) {
                val impuesto = impuestos.item(x) as Element

                val valor = impuesto.getAttributeNode("TotalImpuestosTrasladados")
                if (valor != null){
                    gastoDTO.impuesto = valor.nodeValue
                    //println("Impuestos TotalImpuestosTrasladados: ${impuesto.getAttributeNode("TotalImpuestosTrasladados")}")
                }

            }


//
            return  gastoDTO
        }
    }
}