import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {
    private TreePanel current;
  //  private ArrayList<JButton> children;
    private final JPanel remote;
    private final JFrame remoteFrame;
    public Window() {
        remote = new JPanel();
        remote.setPreferredSize(new Dimension(218,400));
        remote.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        remote.setBackground(Color.gray);
        remoteFrame = new JFrame("REMOTE");
        remoteFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        remoteFrame.setLayout(new FlowLayout());
        remoteFrame.add(remote);
        remoteFrame.pack();
        remoteFrame.setResizable(false);

        makeTree();
        getContentPane().setBackground(Color.gray);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        add(current);
        changeCurrent(current);

        try {
            Thread.sleep(50);
        } catch (InterruptedException ignored) {
        }
    }

    private void changeCurrent(TreePanel newCurrent){
        current.setBackground(Color.white);
        remote.removeAll();
        remote.revalidate();
        remote.repaint();

        for (int i = 0; i < current.getChildrenSize(); i++) {
            current.getChild(i).setBackground(Color.white);
        }
        JButton close = new JButton("close");
        close.addActionListener(e->{
            remoteFrame.dispose();
            dispose();
        });
        remote.add(close);
        remote.add(new JLabel("PANELS TREE"));
        remote.add(new JLabel("green = children of the current panel"));
        remote.add(new JLabel("white = other panels"));
        remote.add(new JLabel("cyan = the current panel"));

        JButton addChildButton = new JButton("add child");
        addChildButton.addActionListener(e-> {
            if (current.getChildrenSize() < 4) { // you can change that to even 100.
                TreePanel child = new TreePanel();
                current.addChild(child);
                current.add(child);
                changeCurrent(current);
            }
        });

        remote.add(addChildButton);
        JButton removeChildButton = new JButton("remove child");
        removeChildButton.addActionListener(e->{
            TreePanel child = current.getChild(current.getChildrenSize() - 1);
                current.removeChild(child);
                current.remove(child);
                changeCurrent(current);
        });
        if (newCurrent.getChild(0) != null)
            remote.add(removeChildButton);


        JButton father = new JButton("father");
        father.addActionListener(e-> changeCurrent(newCurrent.getFather()));
        if (newCurrent.getFather() != null)
            remote.add(father);

        ArrayList<JButton> children = new ArrayList<>();
        for (int i = 0; i < newCurrent.getChildrenSize(); i++) {
            children.add(new JButton("child"+(i+1)));
            int finalI = i;
            children.get(finalI).addActionListener(e->changeCurrent(newCurrent.getChild(finalI)));
            remote.add(children.get(finalI));
        }
        current = newCurrent;
        current.setBackground(Color.cyan);
        for (int i = 0; i < current.getChildrenSize(); i++) {
            current.getChild(i).setBackground(Color.green);
        }
        remote.revalidate();
        remote.repaint();
        repaint();
        revalidate();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        remoteFrame.setLocation(getX()+getWidth(),getY()- remoteFrame.getWidth()/2);
        remoteFrame.setVisible(true);
    }
    private void makeTree() {
        // can be modified:
        TreePanel father = new TreePanel();

        // children
        TreePanel child1 = new TreePanel();
        father.addChild(child1);

        // children of children
        TreePanel child1_1 = new TreePanel();
        child1.addChild(child1_1);

        TreePanel child1_3 = new TreePanel();
        child1.addChild(child1_3);

        // the children of the children of children
        TreePanel child1_1_1 = new TreePanel();
        child1_1.addChild(child1_1_1);
        TreePanel child1_1_2 = new TreePanel();
        child1_1.addChild(child1_1_2);
        TreePanel child1_1_3 = new TreePanel();
        child1_1.addChild(child1_1_3);

        // add features:
        setChildrenForFather(father);

        // must set current to start with:
        setCurrent(father);

    }
    private void setChildrenForFather(TreePanel father){
        // you can add stuff
        for (int i = 0; i < father.getChildrenSize(); i++) {
            TreePanel child = father.getChild(i);
            father.add(child);
            child.setBackground(Color.white);
            setChildrenForFather(child);
        }
    }
    private void setCurrent(TreePanel current) {
        this.current = current;
    }
}