var GridDrop2 = {
    ddGroup : 'noticeSetgridDD',
    copy : true,
    init: function(grid) {
        if (grid.rendered) {
            this.grid = grid;
            this.view = grid.getView();
            this.store = grid.getStore();
        } else {
            grid.on('render', this.init, this);
        }
    },
    /**
     * Returns a custom data object associated with the DOM node that is the target of the event.  By default
     * this looks up the event target in the {@link Ext.dd.Registry}, although you can override this method to
     * provide your own custom lookup.
     * @param {Event} e The event
     * @return {Object} data The custom data
     */
    getTargetFromEvent: function(e) {
//	      Ascertain whether the mousemove is within a grid cell
        var t = e.getTarget(this.view.cellSelector);
        
        if (t) {

//	          We *are* within a grid cell, so ask the View exactly which one,
//	          Extract data from the Model to create a target object for
//	          processing in subsequent onNodeXXXX methods. Note that the target does
//	          not have to be a DOM element. It can be whatever the noNodeXXX methods are
//	          programmed to expect.
            var rowIndex = this.view.findRowIndex(t);
            var columnIndex = this.view.findCellIndex(t);
            if ((rowIndex !== false) && (columnIndex !== false)) {
                return {
                    node: t,
                    record: this.store.getAt(rowIndex),
                    fieldName: this.grid.getColumnModel().getDataIndex(columnIndex)
                }
            }
        }
    },

    onNodeEnter: function(target, dd, e, dragData) {
        delete this.dropOK;
        if (!target) {
            return;
        }

//	      Check that a field is being dragged.
        var f = dragData.selections;
        if (f.length==0) {
            return;
        }

//	      Check whether the data type of the column being dropped on accepts the
//	      dragged field type. If so, set dropOK flag, and highlight the target node.
        //var type = target.record.fields.get(target.fieldName).type;
        /*switch (type) {
            case 'float':
            case 'int':
                if (!f.isXType('numberfield')) {
                    return;
                }
                break;
            case 'date':
                if (!f.isXType('datefield')) {
                    return;
                }
                break;
            case 'boolean':
                if (!f.isXType('checkbox')) {
                    return;
                }
        }*/
        this.dropOK = true;
        Ext.fly(target.node).addClass('x-drop-target-active2');
    },

//  Return the class name to add to the drag proxy. This provides a visual indication
//  of drop allowed or not allowed.
    onNodeOver: function(target, dd, e, dragData) {
        return this.dropOK ? this.dropAllowed : this.dropNotAllowed;
    },

//   nhighlight the target node.
    onNodeOut: function(target, dd, e, dragData) {
        Ext.fly(target.node).removeClass('x-drop-target-active2');
    },

//  Process the drop event if we have previously ascertained that a drop is OK.
    onNodeDrop: function(target, dd, e, dragData) {
        if (this.dropOK) {
            //target.record.set(target.fieldName, dragData.field.getValue());
            return true;
        }
    },

    /**
     * Called while the DropZone determines that a {@link Ext.dd.DragSource} is being dragged over it,
     * but not over any of its registered drop nodes.  The default implementation returns this.dropNotAllowed, so
     * it should be overridden to provide the proper feedback if necessary.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {String} status The CSS class that communicates the drop status back to the source so that the
     * underlying {@link Ext.dd.StatusProxy} can be updated
     */
    onContainerOver : function(dd, e, data){
        return this.dropNotAllowed;
    },

    /**
     * Called when the DropZone determines that a {@link Ext.dd.DragSource} has been dropped on it,
     * but not on any of its registered drop nodes.  The default implementation returns false, so it should be
     * overridden to provide the appropriate processing of the drop event if you need the drop zone itself to
     * be able to accept drops.  It should return true when valid so that the drag source's repair action does not run.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {Boolean} True if the drop was valid, else false
     */
    onContainerDrop : function(dd, e, data){
        return false;
    },

    /**
     * The function a {@link Ext.dd.DragSource} calls once to notify this drop zone that the source is now over
     * the zone.  The default implementation returns this.dropNotAllowed and expects that only registered drop
     * nodes can process drag drop operations, so if you need the drop zone itself to be able to process drops
     * you should override this method and provide a custom implementation.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {String} status The CSS class that communicates the drop status back to the source so that the
     * underlying {@link Ext.dd.StatusProxy} can be updated
     */
    notifyEnter : function(dd, e, data){
        return this.dropNotAllowed;
    },

    /**
     * The function a {@link Ext.dd.DragSource} calls continuously while it is being dragged over the drop zone.
     * This method will be called on every mouse movement while the drag source is over the drop zone.
     * It will call {@link #onNodeOver} while the drag source is over a registered node, and will also automatically
     * delegate to the appropriate node-specific methods as necessary when the drag source enters and exits
     * registered nodes ({@link #onNodeEnter}, {@link #onNodeOut}). If the drag source is not currently over a
     * registered node, it will call {@link #onContainerOver}.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {String} status The CSS class that communicates the drop status back to the source so that the
     * underlying {@link Ext.dd.StatusProxy} can be updated
     */
    notifyOver : function(dd, e, data){
        var n = this.getTargetFromEvent(e);
        if(!n){ // not over valid drop target
            if(this.lastOverNode){
                this.onNodeOut(this.lastOverNode, dd, e, data);
                this.lastOverNode = null;
            }
            return this.onContainerOver(dd, e, data);
        }
        if(this.lastOverNode != n){
            if(this.lastOverNode){
                this.onNodeOut(this.lastOverNode, dd, e, data);
            }
            this.onNodeEnter(n, dd, e, data);
            this.lastOverNode = n;
        }
        return this.onNodeOver(n, dd, e, data);
    },

    /**
     * The function a {@link Ext.dd.DragSource} calls once to notify this drop zone that the source has been dragged
     * out of the zone without dropping.  If the drag source is currently over a registered node, the notification
     * will be delegated to {@link #onNodeOut} for node-specific handling, otherwise it will be ignored.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop target
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag zone
     */
    notifyOut : function(dd, e, data){
        if(this.lastOverNode){
            this.onNodeOut(this.lastOverNode, dd, e, data);
            this.lastOverNode = null;
        }
    },

    /**
     * The function a {@link Ext.dd.DragSource} calls once to notify this drop zone that the dragged item has
     * been dropped on it.  The drag zone will look up the target node based on the event passed in, and if there
     * is a node registered for that event, it will delegate to {@link #onNodeDrop} for node-specific handling,
     * otherwise it will call {@link #onContainerDrop}.
     * @param {Ext.dd.DragSource} source The drag source that was dragged over this drop zone
     * @param {Event} e The event
     * @param {Object} data An object containing arbitrary data supplied by the drag source
     * @return {Boolean} True if the drop was valid, else false
     */
    notifyDrop : function(dd, e, data){
        if(this.lastOverNode){
            this.onNodeOut(this.lastOverNode, dd, e, data);
            this.lastOverNode = null;
        }
        var n = this.getTargetFromEvent(e);
        return n ?
            this.onNodeDrop(n, dd, e, data) :
            this.onContainerDrop(dd, e, data);
    },

    // private
    triggerCacheRefresh : function(){
        Ext.dd.DDM.refreshCache(this.groups);
    }  

}





Ext.apply(GridDrop2,{
	onNodeEnter: function(target, dd, e, dragData) {
        delete this.dropOK;
        if (!target) {
            return;
        }

//      Check that a field is being dragged.
        var f = dragData.selections;
        if (f.length==0) {
            return;
        }

//      Check whether the data type of the column being dropped on accepts the
//      dragged field type. If so, set dropOK flag, and highlight the target node.
        var fieldName = target.fieldName;
        //var type = target.record.fields.get(target.fieldName).type;
        if(fieldName=='a0215a'&&target.record.data.zwqc00!=''&&target.record.data.zwqc00!=null){
        	
        	
        	this.dropOK = true;
            Ext.fly(target.node).addClass('x-drop-target-active2');
            //console.log(target.node)
        }else{
        	return;
        }
    },
    onNodeDrop: function(target, dd, e, dragData) {
    	var DataIndex = target.fieldName;
    	var record = target.record;
    	if(dragData.selections.length!=1){
    		$h.alert("","只能选择一行！");
    		return;
    	}
    	
        if (this.dropOK&&target.fieldName=="a0215a") {
        	
        	
        	return true;
        }
    }
});