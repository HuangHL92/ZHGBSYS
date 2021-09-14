


Ext.grid.StatiGridView = Ext.extend(Ext.grid.GridView, {
    
    statiKey:"id",    
    sRecord:null,    
    
    initData : function(ds, cm){
        Ext.grid.StatiGridView.superclass.initData.call(this,ds, cm);
        
        if(ds){
            if(ds.reader.meta.id){
                statiKey = ds.reader.meta.id;
            }
        }
    },
    
    initTemplates : function(){
        Ext.grid.StatiGridView.superclass.initTemplates.call(this);
        
         if(!this.startGroup){
            this.startGroup = new Ext.XTemplate(
                '<div id="{groupId}" class="x-grid-group {cls}">',                    
                    '<div id="{groupId}-bd" class="x-grid-group-body">'
            );
        }
        this.startGroup.compile();
        this.endGroup = '</div></div>';
        
        if(!this.rowTpl){
            this.rowTpl = new Ext.Template(
                '<div class="x-grid3-summary-row" style="{tstyle}">',
                '<table class="x-grid3-summary-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
                    '<tbody><tr>{cells}</tr>',
                '</table></div>'
            );
            this.rowTpl.disableFormats = true;
        }
        this.rowTpl.compile();

        if(!this.cellTpl){
            this.cellTpl = new Ext.Template(
                '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}">',
                '<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on">{value}</div>',
                "</td>"
            );
            this.cellTpl.disableFormats = true;
        }
        this.cellTpl.compile();
    },
    
    onDataChange : function(){
        if(this.ds){
            var tmpRecord = null;
            var length = this.ds.getCount();
            
            if(length > 0){
                tmpRecord = this.ds.getAt(length-1);
                this.ds.un("remove", this.onRemove, this);
                
                if(this.isStatiRecord(tmpRecord)){
                    this.sRecord = tmpRecord;                    
                    this.ds.data.removeAt(length-1);                    
                }else if(length > 1){
                    tmpRecord = this.ds.getAt(0);
                    if(this.isStatiRecord(tmpRecord)){
                        this.sRecord = tmpRecord;
                        this.ds.data.removeAt(0); 
                    }
                }
                
                this.ds.on("remove", this.onRemove, this);                
            }
        }
        
        Ext.grid.StatiGridView.superclass.onDataChange.call(this);
    },
    
    isStatiRecord : function (r) {
      if(r.data[this.statiKey] === undefined || r.data[this.statiKey]===null || r.data[this.statiKey]===""){
           return true;
      }
      
      return false;
    },
    // private
    doGroupStart : function(buf, g, cs, ds, colCount){
        //buf[buf.length] = this.startGroup.apply(g);
    },

    // private
    doGroupEnd : function(buf, g, cs, ds, colCount){
             
        var cfg = this.grid.colModel.config;
        
        var tbuf = [], c, p = {},cf, last = cs.length-1;
        
        for(var i = 0, len = cs.length; i < len; i++){
            c = cs[i];  
            cf = cfg[i];          
            p.id = c.id;
            p.style = c.style;
            p.css = i == 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '');
            
            if(c.renderer && ds.data[c.name]){
                p.value = c.renderer(ds.data[c.name], p, ds);
            }else {
                p.value = ds.data[c.name];
            }
            
            if(p.value == undefined || p.value === "") p.value = "&#160;";
            
            tbuf[tbuf.length] = this.cellTpl.apply(p);
        }

        buf[buf.length] = this.rowTpl.apply({
            tstyle: 'width:'+this.getTotalWidth()+';',
            cells: tbuf.join('')
        });
        //buf[buf.length] = this.endGroup;
    },
    
    
    doRender : function(cs, rs, ds, startRow, colCount, stripe){         
        var buf = [];       
        
        buf[buf.length] = Ext.grid.GroupingView.superclass.doRender.call(this, cs, rs, ds, startRow, colCount, stripe);
        
        if(ds.getCount() === rs.length && this.sRecord){
                        
            var sg = { 
                groupId: ds.getTotalCount()+1,         
                cls: ""                    
            };            
                     
            this.doGroupStart(buf, sg);           
            this.doGroupEnd(buf, sg, cs, this.sRecord);
        }
        
        return buf.join('');
      
    }
});





