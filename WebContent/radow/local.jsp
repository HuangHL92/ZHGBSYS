<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<script>
/**
 * 获取显示在个人工作台上导航栏位置的软件版本信息等内容
 */
function getWpfNavBarSetInfo(){
	return "版本号：6.0";
}

/**
Ext.override(Ext.layout.ToolbarLayout, {
	fitToSize : function (n) {
		if (this.container.enableOverflow === false) {
			return
		}
		var m = n.dom.clientWidth;
		var b = this.lastWidth || 0;
		this.lastWidth = m;
		var d = n.dom.firstChild.offsetWidth;
		var l = m - this.triggerWidth;
		var length=this.container.items.items.length;
		if(length<=8 && this.container.getXType() =='toolbar'){
			l=3000;
			d=2500;
		}
		var k = -1;
		if (d > m || (this.hiddens && m >= b)) {
			var e,
			h = this.container.items.items,
			g = h.length,
			j;
			var a = 0;
			for (e = 0; e < g; e++) {
				j = h[e];
				if (!j.isFill) {
					a += this.getItemWidth(j);
					if (a > l) {
						if (!j.xtbHidden) {
							this.hideItem(j)
						}
					} else {
						if (j.xtbHidden) {
							this.unhideItem(j)
						}
					}
				}
			}
		}
		if (this.hiddens) {
			this.initMore();
			if (!this.lastOverflow) {
				this.container.fireEvent("overflowchange", this.container, true);
				this.lastOverflow = true
			}
		} else {
			if (this.more) {
				this.clearMenu();
				this.more.destroy();
				delete this.more;
				if (this.lastOverflow) {
					this.container.fireEvent("overflowchange", this.container, false);
					this.lastOverflow = false
				}
			}
		}
	}
});
 */

</script>