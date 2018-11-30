package org.primefaces.atlantis.component;

import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.renderkit.CoreRenderer;
import org.primefaces.util.WidgetBuilder;

public class TabMenuRenderer extends CoreRenderer {
    
    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        TabMenu tabMenu = (TabMenu) component;
        ResponseWriter writer = context.getResponseWriter();  
        List<UIComponent> children = tabMenu.getChildren();
        String clientId = tabMenu.getClientId(context);
        
        writer.startElement("div", tabMenu);
        writer.writeAttribute("id", clientId , "id");
        writer.writeAttribute("class", "layout-tabmenu", null);
        
        writer.startElement("ul", tabMenu);
        writer.writeAttribute("class", "layout-tabmenu-nav", null);
        
        for(UIComponent child : children ){
            if(child.isRendered() && child instanceof Tab) {
                Tab tab = (Tab) child;
                writer.startElement("li", tab);
                    writer.startElement("a", tab);
                    writer.writeAttribute("href", "#", null);
                        writer.startElement("i", tab);
                        writer.writeAttribute("class", tab.getIcon(), null);
                        writer.endElement("i");
                        
                        writer.startElement("div", null);
                            writer.writeAttribute("class", "layout-tabmenu-tooltip", null);
                            writer.startElement("div", null);
                            writer.writeAttribute("class", "layout-tabmenu-tooltip-arrow", null);
                            writer.endElement("div");
                            writer.startElement("div", null);
                            writer.writeAttribute("class", "layout-tabmenu-tooltip-text", null);
                            writer.writeText(tab.getTitle(), null);
                            writer.endElement("div");
                        writer.endElement("div");
                    writer.endElement("a");
                writer.endElement("li");
            }
        }
        
        writer.endElement("ul");
        
        writer.startElement("div", tabMenu);
        writer.writeAttribute("class", "layout-tabmenu-contents", null);
        for(int i = 0; i < children.size(); i++) {
            Tab tab = (Tab) children.get(i);
            
            if(tab.isRendered()) {
                writer.startElement("div", tabMenu);
                writer.writeAttribute("class", "layout-tabmenu-content", null);

                    writer.startElement("div", tabMenu);
                    writer.writeAttribute("class", "layout-submenu-title clearfix", null);
                        writer.startElement("span", tab);
                        writer.writeText(tab.getTitle(), null);
                        writer.endElement("span");

                        writer.startElement("a", tab);
                        writer.writeAttribute("href", "#", null);
                        writer.writeAttribute("class", "menu-button", null);
                            writer.startElement("i", tab);
                            writer.writeAttribute("class", "fa fa-bars", null);
                            writer.endElement("i");
                        writer.endElement("a");
                        
                        writer.startElement("a", tab);
                        writer.writeAttribute("href", "#", null);
                        writer.writeAttribute("class", "menu-pin-button", null);
                        writer.startElement("i", null);
                        writer.writeAttribute("class", "fa fa-thumb-tack fa-rotate-90", null);
                        writer.endElement("i");
                        writer.endElement("a");
                    writer.endElement("div");

                    writer.startElement("div", tabMenu);
                    writer.writeAttribute("class", "layout-submenu-content", null);
                        writer.startElement("div", null);
                        writer.writeAttribute("class", "nano", null);
                            writer.startElement("div", null);
                            writer.writeAttribute("class", "nano-content menu-scroll-content", null);
                                tab.encodeAll(context);
                            writer.endElement("div");
                        writer.endElement("div");
                    writer.endElement("div");


                writer.endElement("div");
            }
        }
        writer.endElement("div");
        
        writer.endElement("div");
        
        WidgetBuilder wb = getWidgetBuilder(context);
        wb.init("Atlantis", tabMenu.resolveWidgetVar(), clientId).finish();
    }
    
    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        //Rendering happens on encodeEnd
    }
    
    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
