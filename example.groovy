//@Library(['github.com/shared-library']) _

//def exampleMethod() {
//    def file = new File ("http://13.52.98.219:8081/repository/maven_nikhil/com/marsh/et2/0.0.3-SNAPSHOT/maven-metadata.xml")
//def xml = new XmlParser.parseText(file)
//println xml.@groupId
//}

//return this
import hudson.model.*
import hudson.EnvVars
import groovy.json.JsonSlurperClassic
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import java.net.URL
import java.lang.Object
import groovy.util.*
import groovy.xml.MarkupBuilder

def exampleMethod()
{
    
	def xml = new XmlParser()
  def file = parser.parse ("http://13.52.98.219:8081/repository/maven_nikhil/com/marsh/et2/0.0.3-SNAPSHOT/maven-metadata.xml")
  
  doc.metadata.each{
         bk->
         print("Movie Name:")
        def x= println "${bk['@groupId']}"
    return x
  }
}
