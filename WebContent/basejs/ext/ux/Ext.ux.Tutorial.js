/** 
 * 
 * LICENSE: None 
 * 
 * @author Mitchell Simoens 
 * @copyright 2009 Mitchell Simoens 
 * @license Private use only 
 * @since November 10 2009 
 */  
/** 
 * 
 * @class Ext.ux.Tutorial 
 * @extends Ext.Window 
 * 
 */  
  
Ext.namespace("Ext.ux");  
Ext.ux.Tutorial = Ext.extend(Ext.Window, {  
    /** 
     * Title of the window 
     * 
     * @type String 
     */  
    title: "Tutorial",  
    /** 
     * Width of the window 
     * 
     * @type Int 
     */  
    width: 800,  
    /** 
     * Height of the window 
     * 
     * @type Int 
     */  
    height: 800,  
    /** 
     * Set to true to mask everything behind it 
     * 
     * @type Boolean 
     */  
    modal: false,  
    /** 
     * Set to false to display a light color to the background 
     * 
     * @type Boolean 
     */  
    plain: true,  
    /** 
     * Set to true to allow the resizing of the window 
     * 
     * @type Boolean 
     */  
    resizable: false,  
    /** 
     * Set to true to allow to close the window 
     * 
     * @type Boolean 
     */  
    closable: true,  
    /** 
     * Set to true to allow the window to collapse 
     * 
     * @type Boolean 
     */  
    collapsible: false,  
    /** 
     * Set to true to constrain the movement of 
     * the window to it's containing element 
     * 
     * @type Boolean 
     */  
    constrain: true,  
    /** 
     * Set to true to allow dragging of the window 
     * 
     * @type Boolean 
     */  
    draggable: true,  
    /** 
     * Set to true to show a shadow 
     * 
     * @type Boolean 
     */  
    shadow: false,  
    /** 
     * Offset of the shadow in pixels 
     * 
     * @type Int 
     */  
    shadowOffset: 4,  
    /** 
     * DOM ID of whre to render the window to 
     * 
     * @type String 
     */  
    renderTo: null,  
    /** 
     * Array of steps. Each step is created as a seperate Panel 
     * 
     * @type Array 
     */  
    steps: null,  
    /** 
     * Set true to animate the updating of the ProgressBar 
     * 
     * @type Boolean 
     */  
    pbarAnim: true,  
    /** 
     * Height of the ProgressBar 
     * 
     * @type Int 
     */  
    pbarHeight: 20,  
    /** 
     * Function that handles the cancel 
     * 
     * @type Function 
     */  
    cancel: function() {  
        this.close();  
    },  
    /** 
     * Function that handles the finish 
     * 
     * @type Function 
     */  
    finish: Ext.emptyFn,  
    /** 
     * 
     */  
    initComponent: function() {  
        if (this.shadow === true) {  
            this.floating = true;  
        }  
        this.pbar = new Ext.Panel({  
            id: "progressPanel",  
            frame: false,  
            border: false,  
            height: this.pbarHeight,  
            items: [new Ext.ProgressBar({  
                id: "pbar",  
                animate: this.pbarAnim  
            })]  
        });  
        this.cPanel = new Ext.Panel({  
            id: "cardPanel",  
            frame: false,  
            border: false,  
            layout: "slide",  
            flex: 1,  
            layoutConfig: {  
                easing: "none",  
                duration: .5,  
                opacity: .1  
            },  
            activeItem: 0,  
            listeners: {  
                "add": function(thisPanel, addedCmp, Index) {  
                    addedCmp.initialConfig.index = Index;  
                }  
            }  
        });  
        if (this.steps) {  
            if (this.steps.length > 0) {  
                for (var i = 0; i < (this.steps.length); i++) {  
                    this.createSteps(i);  
                }  
            }  
        }  
        var config = {  
            layout: "vbox",  
            items : [this.pbar, this.cPanel],  
            layoutConfig: {  
                align: "stretch",  
                pack: "start"  
            },  
            bbar: [{  
                id: "move-prev",  
                text: "上一步",  
                disabled: true,  
                hidden: true,  
                handler: this.navHandler.createDelegate(this, [-1])  
            },{  
                id: "cancel",  
                text: "关闭",  
                handler: function(){  
                    this.ownerCt.ownerCt.cancel();  
                }  
            },"->",{  
                id: "move-next",  
                text: "下一步",  
                disabled: false,  
                handler: this.navHandler.createDelegate(this, [1])  
            },{  
                id: "finish",  
                text: "完成",  
                disabled: false,  
                hidden: true,  
                handler: function() {  
                    this.ownerCt.ownerCt.finish();  
                }  
            }]  
        };  
        Ext.apply(this, Ext.apply(this.initialConfig, config));  
        Ext.ux.Tutorial.superclass.initComponent.apply(this, arguments);  
        this.on("show", this.onShow, this);  
    },  
    /** 
     * 
     */  
    onShow: function() {  
        Ext.ux.Tutorial.superclass.onShow.apply(this, arguments);  
        var tmp = "步骤 1/"+this.cPanel.items.length+" /"+this.cPanel.layout.activeItem.initialConfig.title1+"/";  
        Ext.getCmp("pbar").updateProgress((this.cPanel.layout.activeItem.initialConfig.index+1)/this.cPanel.items.length, tmp);  
    },  
    /** 
     * 
     */  
    navHandler: function(direction) {  
        currStepPanel = this.cPanel.layout.activeItem;  
        otherStepPanel = Ext.getCmp("card-"+(currStepPanel.initialConfig.index+direction));  
        var tmp = "步骤 "+(otherStepPanel.initialConfig.index+1)+"/"+this.cPanel.items.length+" "+otherStepPanel.initialConfig.title1;  
        Ext.getCmp("pbar").updateProgress((otherStepPanel.initialConfig.index+1)/this.cPanel.items.length, tmp);  
        this.cPanel.getLayout().setActiveItem(currStepPanel.initialConfig.index+direction);  
        if (otherStepPanel.initialConfig.index === 0) {  
            Ext.getCmp("move-prev").disable();  
            Ext.getCmp("move-prev").hide();  
              
            Ext.getCmp("cancel").enable();  
            Ext.getCmp("cancel").show();  
        } else {  
            Ext.getCmp("move-prev").enable();  
            Ext.getCmp("move-prev").show();  
              
            Ext.getCmp("cancel").disable();  
            Ext.getCmp("cancel").hide();  
        }  
        if (otherStepPanel.initialConfig.index === (this.cPanel.items.length-1)) {  
            Ext.getCmp("move-next").disable();  
            Ext.getCmp("move-next").hide();  
              
            Ext.getCmp("finish").enable();  
            Ext.getCmp("finish").show();  
        } else {  
            Ext.getCmp("move-next").enable();  
            Ext.getCmp("move-next").show();  
              
            Ext.getCmp("finish").disable();  
            Ext.getCmp("finish").hide();  
        }  
    },  
    /** 
     * 
     */  
    createSteps: function(i) {  
        if (!this.steps[i].bodyStyle) {  
            this.steps[i].bodyStyle = "";  
        }  
        if (!this.steps[i].cls) {  
            this.steps[i].cls = "";  
        }  
        this.cPanel.add(new Ext.Panel({  
            id: "card-"+i,  
            frame: false,  
            border:false,  
            bodyStyle: this.steps[i].bodyStyle,  
            cls: this.steps[i].cls,  
            title1: this.steps[i].title,  
            items:this.steps[i].items  
//          html: this.steps[i].html  
        }));  
    }  
});  
Ext.ComponentMgr.registerType("ux-tutorial", Ext.ux.Tutorial);  