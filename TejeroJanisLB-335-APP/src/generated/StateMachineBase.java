/**
 * This class contains generated code from the Codename One Designer, DO NOT MODIFY!
 * This class is designed for subclassing that way the code generator can overwrite it
 * anytime without erasing your changes which should exist in a subclass!
 * For details about this file and how it works please read this blog post:
 * http://codenameone.blogspot.com/2010/10/ui-builder-class-how-to-actually-use.html
*/
package generated;

import com.codename1.ui.*;
import com.codename1.ui.util.*;
import com.codename1.ui.plaf.*;
import java.util.Hashtable;
import com.codename1.ui.events.*;

public abstract class StateMachineBase extends UIBuilder {
    private Container aboutToShowThisContainer;
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    /**
    * @deprecated use the version that accepts a resource as an argument instead
    
**/
    protected void initVars() {}

    protected void initVars(Resources res) {}

    public StateMachineBase(Resources res, String resPath, boolean loadTheme) {
        startApp(res, resPath, loadTheme);
    }

    public Container startApp(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    if(resPath.endsWith(".res")) {
                        res = Resources.open(resPath);
                        System.out.println("Warning: you should construct the state machine without the .res extension to allow theme overlays");
                    } else {
                        res = Resources.openLayered(resPath);
                    }
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        if(res != null) {
            setResourceFilePath(resPath);
            setResourceFile(res);
            initVars(res);
            return showForm(getFirstFormName(), null);
        } else {
            Form f = (Form)createContainer(resPath, getFirstFormName());
            initVars(fetchResourceFile());
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    protected String getFirstFormName() {
        return "Watchlist";
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    res = Resources.openLayered(resPath);
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        return createContainer(resPath, "Watchlist");
    }

    protected void initTheme(Resources res) {
            String[] themes = res.getThemeResourceNames();
            if(themes != null && themes.length > 0) {
                UIManager.getInstance().setThemeProps(res.getTheme(themes[0]));
            }
    }

    public StateMachineBase() {
    }

    public StateMachineBase(String resPath) {
        this(null, resPath, true);
    }

    public StateMachineBase(Resources res) {
        this(res, null, true);
    }

    public StateMachineBase(String resPath, boolean loadTheme) {
        this(null, resPath, loadTheme);
    }

    public StateMachineBase(Resources res, boolean loadTheme) {
        this(res, null, loadTheme);
    }

    public com.codename1.ui.Button findButton1(Component root) {
        return (com.codename1.ui.Button)findByName("Button1", root);
    }

    public com.codename1.ui.Button findButton1() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel(Component root) {
        return (com.codename1.ui.Label)findByName("Label", root);
    }

    public com.codename1.ui.Label findLabel() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel2(Component root) {
        return (com.codename1.ui.Label)findByName("Label2", root);
    }

    public com.codename1.ui.Label findLabel2() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findAccountValueLbl(Component root) {
        return (com.codename1.ui.Label)findByName("accountValueLbl", root);
    }

    public com.codename1.ui.Label findAccountValueLbl() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("accountValueLbl", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("accountValueLbl", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findDataContainer(Component root) {
        return (com.codename1.ui.Container)findByName("dataContainer", root);
    }

    public com.codename1.ui.Container findDataContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("dataContainer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("dataContainer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findResultLbl(Component root) {
        return (com.codename1.ui.Label)findByName("resultLbl", root);
    }

    public com.codename1.ui.Label findResultLbl() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("resultLbl", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("resultLbl", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer(Component root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.Container findContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findIconContainer(Component root) {
        return (com.codename1.ui.Container)findByName("iconContainer", root);
    }

    public com.codename1.ui.Container findIconContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("iconContainer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("iconContainer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findProfileIconLbl(Component root) {
        return (com.codename1.ui.Label)findByName("profileIconLbl", root);
    }

    public com.codename1.ui.Label findProfileIconLbl() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("profileIconLbl", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("profileIconLbl", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel4(Component root) {
        return (com.codename1.ui.Label)findByName("Label4", root);
    }

    public com.codename1.ui.Label findLabel4() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label4", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label4", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton(Component root) {
        return (com.codename1.ui.Button)findByName("Button", root);
    }

    public com.codename1.ui.Button findButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel6(Component root) {
        return (com.codename1.ui.Label)findByName("Label6", root);
    }

    public com.codename1.ui.Label findLabel6() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label6", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label6", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton3(Component root) {
        return (com.codename1.ui.Button)findByName("Button3", root);
    }

    public com.codename1.ui.Button findButton3() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button3", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button3", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findFreeFundsLbl(Component root) {
        return (com.codename1.ui.Label)findByName("freeFundsLbl", root);
    }

    public com.codename1.ui.Label findFreeFundsLbl() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("freeFundsLbl", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("freeFundsLbl", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton2(Component root) {
        return (com.codename1.ui.Button)findByName("Button2", root);
    }

    public com.codename1.ui.Button findButton2() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findPortfolioValueLbl(Component root) {
        return (com.codename1.ui.Label)findByName("portfolioValueLbl", root);
    }

    public com.codename1.ui.Label findPortfolioValueLbl() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("portfolioValueLbl", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("portfolioValueLbl", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findCard(Component root) {
        return (com.codename1.ui.Container)findByName("card", root);
    }

    public com.codename1.ui.Container findCard() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("card", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("card", aboutToShowThisContainer);
        }
        return cmp;
    }

    protected void exitForm(Form f) {
        if("Account".equals(f.getName())) {
            exitAccount(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Watchlist".equals(f.getName())) {
            exitWatchlist(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Graph".equals(f.getName())) {
            exitGraph(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Portfolio".equals(f.getName())) {
            exitPortfolio(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Test".equals(f.getName())) {
            exitTest(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Find".equals(f.getName())) {
            exitFind(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void exitAccount(Form f) {
    }


    protected void exitWatchlist(Form f) {
    }


    protected void exitGraph(Form f) {
    }


    protected void exitPortfolio(Form f) {
    }


    protected void exitTest(Form f) {
    }


    protected void exitFind(Form f) {
    }

    protected void beforeShow(Form f) {
    aboutToShowThisContainer = f;
        if("Account".equals(f.getName())) {
            beforeAccount(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Watchlist".equals(f.getName())) {
            beforeWatchlist(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Graph".equals(f.getName())) {
            beforeGraph(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Portfolio".equals(f.getName())) {
            beforePortfolio(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Test".equals(f.getName())) {
            beforeTest(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Find".equals(f.getName())) {
            beforeFind(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void beforeAccount(Form f) {
    }


    protected void beforeWatchlist(Form f) {
    }


    protected void beforeGraph(Form f) {
    }


    protected void beforePortfolio(Form f) {
    }


    protected void beforeTest(Form f) {
    }


    protected void beforeFind(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        aboutToShowThisContainer = c;
        if("Account".equals(c.getName())) {
            beforeContainerAccount(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Watchlist".equals(c.getName())) {
            beforeContainerWatchlist(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Graph".equals(c.getName())) {
            beforeContainerGraph(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Portfolio".equals(c.getName())) {
            beforeContainerPortfolio(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Test".equals(c.getName())) {
            beforeContainerTest(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Find".equals(c.getName())) {
            beforeContainerFind(c);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void beforeContainerAccount(Container c) {
    }


    protected void beforeContainerWatchlist(Container c) {
    }


    protected void beforeContainerGraph(Container c) {
    }


    protected void beforeContainerPortfolio(Container c) {
    }


    protected void beforeContainerTest(Container c) {
    }


    protected void beforeContainerFind(Container c) {
    }

    protected void postShow(Form f) {
        if("Account".equals(f.getName())) {
            postAccount(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Watchlist".equals(f.getName())) {
            postWatchlist(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Graph".equals(f.getName())) {
            postGraph(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Portfolio".equals(f.getName())) {
            postPortfolio(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Test".equals(f.getName())) {
            postTest(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Find".equals(f.getName())) {
            postFind(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void postAccount(Form f) {
    }


    protected void postWatchlist(Form f) {
    }


    protected void postGraph(Form f) {
    }


    protected void postPortfolio(Form f) {
    }


    protected void postTest(Form f) {
    }


    protected void postFind(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("Account".equals(c.getName())) {
            postContainerAccount(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Watchlist".equals(c.getName())) {
            postContainerWatchlist(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Graph".equals(c.getName())) {
            postContainerGraph(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Portfolio".equals(c.getName())) {
            postContainerPortfolio(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Test".equals(c.getName())) {
            postContainerTest(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Find".equals(c.getName())) {
            postContainerFind(c);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void postContainerAccount(Container c) {
    }


    protected void postContainerWatchlist(Container c) {
    }


    protected void postContainerGraph(Container c) {
    }


    protected void postContainerPortfolio(Container c) {
    }


    protected void postContainerTest(Container c) {
    }


    protected void postContainerFind(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("Account".equals(rootName)) {
            onCreateAccount();
            aboutToShowThisContainer = null;
            return;
        }

        if("Watchlist".equals(rootName)) {
            onCreateWatchlist();
            aboutToShowThisContainer = null;
            return;
        }

        if("Graph".equals(rootName)) {
            onCreateGraph();
            aboutToShowThisContainer = null;
            return;
        }

        if("Portfolio".equals(rootName)) {
            onCreatePortfolio();
            aboutToShowThisContainer = null;
            return;
        }

        if("Test".equals(rootName)) {
            onCreateTest();
            aboutToShowThisContainer = null;
            return;
        }

        if("Find".equals(rootName)) {
            onCreateFind();
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void onCreateAccount() {
    }


    protected void onCreateWatchlist() {
    }


    protected void onCreateGraph() {
    }


    protected void onCreatePortfolio() {
    }


    protected void onCreateTest() {
    }


    protected void onCreateFind() {
    }

    protected Hashtable getFormState(Form f) {
        Hashtable h = super.getFormState(f);
        if("Account".equals(f.getName())) {
            getStateAccount(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Watchlist".equals(f.getName())) {
            getStateWatchlist(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Graph".equals(f.getName())) {
            getStateGraph(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Portfolio".equals(f.getName())) {
            getStatePortfolio(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Test".equals(f.getName())) {
            getStateTest(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Find".equals(f.getName())) {
            getStateFind(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

            return h;
    }


    protected void getStateAccount(Form f, Hashtable h) {
    }


    protected void getStateWatchlist(Form f, Hashtable h) {
    }


    protected void getStateGraph(Form f, Hashtable h) {
    }


    protected void getStatePortfolio(Form f, Hashtable h) {
    }


    protected void getStateTest(Form f, Hashtable h) {
    }


    protected void getStateFind(Form f, Hashtable h) {
    }

    protected void setFormState(Form f, Hashtable state) {
        super.setFormState(f, state);
        if("Account".equals(f.getName())) {
            setStateAccount(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Watchlist".equals(f.getName())) {
            setStateWatchlist(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Graph".equals(f.getName())) {
            setStateGraph(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Portfolio".equals(f.getName())) {
            setStatePortfolio(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Test".equals(f.getName())) {
            setStateTest(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Find".equals(f.getName())) {
            setStateFind(f, state);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void setStateAccount(Form f, Hashtable state) {
    }


    protected void setStateWatchlist(Form f, Hashtable state) {
    }


    protected void setStateGraph(Form f, Hashtable state) {
    }


    protected void setStatePortfolio(Form f, Hashtable state) {
    }


    protected void setStateTest(Form f, Hashtable state) {
    }


    protected void setStateFind(Form f, Hashtable state) {
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        Container leadParentContainer = c.getParent().getLeadParent();
        if(leadParentContainer != null && leadParentContainer.getClass() != Container.class) {
            c = c.getParent().getLeadParent();
        }
        if(rootContainerName == null) return;
        if(rootContainerName.equals("Test")) {
            if("Button".equals(c.getName())) {
                onTest_ButtonAction(c, event);
                return;
            }
            if("Button1".equals(c.getName())) {
                onTest_Button1Action(c, event);
                return;
            }
            if("Button2".equals(c.getName())) {
                onTest_Button2Action(c, event);
                return;
            }
            if("Button3".equals(c.getName())) {
                onTest_Button3Action(c, event);
                return;
            }
        }
    }

      protected void onTest_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onTest_Button1Action(Component c, ActionEvent event) {
      }

      protected void onTest_Button2Action(Component c, ActionEvent event) {
      }

      protected void onTest_Button3Action(Component c, ActionEvent event) {
      }

}
