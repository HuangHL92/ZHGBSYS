    function moveUp(_a){
     var _row=_a.parentNode.parentNode;
     if(_row.previousSibling && _row.previousSibling.id!=="")swapNode(_row,_row.previousSibling);
    }
    function moveDown(_a){
     var _row=_a.parentNode.parentNode;
     if(_row.nextSibling)swapNode(_row,_row.nextSibling);
    }
    function swapNode(node1,node2){
     var _parent=node1.parentNode;
     var _t1=node1.nextSibling;
     var _t2=node2.nextSibling;
     if(_t1)_parent.insertBefore(node2,_t1);
     else _parent.appendChild(node2);
     if(_t2)_parent.insertBefore(node1,_t2);
     else _parent.appendChild(node1);
    }