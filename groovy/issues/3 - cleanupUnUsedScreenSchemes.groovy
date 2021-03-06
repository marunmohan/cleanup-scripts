boolean isPreview = true
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.screen.FieldScreenSchemeManager
import org.apache.log4j.Logger
import org.apache.log4j.Level

def log = Logger.getLogger("com.gonchik.scripts.groovy.cleanupUsUsedIssueTypeScreenManager")
log.setLevel(Level.DEBUG)

def schemeManager = ComponentAccessor.getComponent(FieldScreenSchemeManager.class)
def sb = new StringBuilder()

if (isPreview == true) {
    sb.append("<b>Please, note it works as preview. For execute change variable isPreview = true </b><br/><br/>\n")
} else {
    sb.append("<b>Please, note it works in execute mode</b><br/><br/>\n")
}
sb.append("Deleted screen schemes with no screens projects:<br/><br/>\n")
schemeManager.getFieldScreenSchemes().each {
    try {
        if (schemeManager.getFieldScreenSchemeItems(it).size() < 1) {
            if (!isPreview) {
                schemeManager.removeFieldScreenScheme(it)
            }
        }
    }
    catch (Exception e) {
        sb.append("Error: " + e + "<br/>\n")
    }
}
schemeManager.refresh()
return sb.toString()