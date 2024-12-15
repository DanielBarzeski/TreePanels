import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TreePanel extends JPanel {
    private TreePanel father;
    private final ArrayList<TreePanel> children;
    public TreePanel() {
        this.children = new ArrayList<>();
    }
    public void addChild(TreePanel child){
        if (child.father != null)
            child.father.remove(child);
        this.children.add(child);
        child.father = this;
    }
    public void removeChild(TreePanel child){
        if (child.father == this){
            this.children.remove(child);
            child.father = null;
        }
    }
    public TreePanel getChild(int index) {
        if (index < children.size())
            return children.get(index);
        return null;
    }
    public TreePanel getFather() {
        return this.father;
    }
    public int getChildrenSize() {
        return children.size();
    }
    // not must:
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0,0,getPreferredSize().width-1,getPreferredSize().height-1);
    }
}
