(function(b) {
	b.fn.ajaxComboBox = function(d, c) {
		return this.each(function() {
					jQuery(this).height(18);
					new a(this, d, c)
				})
	};
	function a(c, e, d) {
		this._setOption(c, e, d);
		this._setMessage();
		this._setCssClass();
		this._setProp();
		this._setElem(c);
		this._setButtonAttrDefault();
		this._setButtonPosition();
		this._setInitRecord();
		this._ehButton();
		this._ehComboInput();
		this._ehWhole();
		this._ehTextArea();
		if (this.option.shorten_btn) {
			this._findUrlToShorten(this)
		}
	}
	b.extend(a.prototype, {
		_setOption : function(c, e, d) {
			d = this._setOption1st(e, d);
			d = this._setOption2nd(c, d);
			this.option = d
		},
		_setOption1st : function(d, c) {
			return b.extend({
						source : d,
						lang : "cn",
						plugin_type : "combobox",
						init_record : false,
						db_table : "tbl",
						field : "name",
						condition : "",
						lastflg : "lastflg",
						and_or : "AND",
						per_page : 10,
						navi_num : 5,
						primary_key : "id",
						button_img : "../../images/btn.png",
						bind_to : false,
						navi_simple : false,
						sub_info : false,
						sub_as : {},
						show_field : "",
						hide_field : "",
						select_only : false,
						is_strict : false,
						tags : false,
						shorten_btn : false,
						shorten_src : "lib/bitly.php",
						shorten_min : 20,
						shorten_reg : false
					}, c)
		},
		_setOption2nd : function(d, f) {
			f.search_field = (f.search_field == undefined)
					? f.field
					: f.search_field;
			f.and_or = f.and_or.toUpperCase();
			var c = ["hide_field", "show_field", "search_field"];
			for (var e = 0; e < c.length; e++) {
				f[c[e]] = this._strToArray(f[c[e]])
			}
			f.order_by = (f.order_by == undefined)
					? f.search_field
					: f.order_by;
			f.order_by = this._setOrderbyOption(f.order_by, f.field);
			if (f.plugin_type == "textarea") {
				f.shorten_reg = this._setRegExpShort(f.shorten_reg,
						f.shorten_min)
			}
			if (f.tags) {
				f.tags = this._setTagPattern(f)
			}
			return f
		},
		_strToArray : function(c) {
			return c.replace(/[\s　]+/g, "").split(",")
		},
		_setRegExpShort : function(c, e) {
			if (c) {
				return c
			}
			var d = "(?:^|[\\s|　\\[(<「『（【［＜〈《]+)";
			d += "(";
			d += "https:\\/\\/[^\\s|　\\])>」』）】］＞〉》]{" + (e - 7) + ",}";
			d += "|";
			d += "http:\\/\\/[^\\s|　\\])>」』）】］＞〉》]{" + (e - 6) + ",}";
			d += "|";
			d += "ftp:\\/\\/[^\\s|　\\])>」』）】］＞〉》]{" + (e - 5) + ",}";
			d += ")";
			return new RegExp(d, "g")
		},
		_setTagPattern : function(d) {
			for (var c = 0; c < d.tags.length; c++) {
				d.tags[c] = this._setTagOptions(d, c);
				d.tags[c].pattern = this._setRegExpTag(d.tags[c].pattern,
						d.tags[c].space)
			}
			return d.tags
		},
		_setTagOptions : function(f, d) {
			f.tags[d] = b.extend({
						space : [true, true],
						db_table : f.db_table,
						field : f.field,
						search_field : f.search_field,
						primary_key : f.primary_key,
						sub_info : f.sub_info,
						sub_as : f.sub_as,
						show_field : f.show_field,
						hide_field : f.hide_field
					}, f.tags[d]);
			var c = ["hide_field", "show_field", "search_field"];
			for (var e = 0; e < c.length; e++) {
				if (typeof f.tags[d][c[e]] != "object") {
					f.tags[d][c[e]] = this._strToArray(f.tags[d][c[e]])
				}
			}
			f.tags[d].order_by = (f.tags[d].order_by == undefined)
					? f.order_by
					: this._setOrderbyOption(f.tags[d].order_by,
							f.tags[d].field);
			return f.tags[d]
		},
		_setRegExpTag : function(e, d) {
			var f = e[0].replace(/[\s\S]*/, this._escapeForReg);
			var c = e[1].replace(/[\s\S]*/, this._escapeForReg);
			return {
				left : e[0],
				right : e[1],
				reg_left : new RegExp(f + "((?:(?!" + f + "|" + c
						+ ")[^\\s　])*)$"),
				reg_right : new RegExp("^((?:(?!" + f + "|" + c + ")[^\\s　])+)"),
				space_left : new RegExp("^" + f + "$|[\\s　]+" + f + "$"),
				space_right : new RegExp("^$|^[\\s　]+"),
				comp_right : new RegExp("^" + c)
			}
		},
		_escapeForReg : function(c) {
			return "\\u" + (65536 + c.charCodeAt(0)).toString(16).slice(1)
		},
		_setOrderbyOption : function(d, g) {
			var c = [];
			if (typeof d == "object") {
				for (var e = 0; e < d.length; e++) {
					var f = b.trim(d[e]).split(" ");
					c[e] = (f.length == 2) ? f : [f[0], "ASC"]
				}
			} else {
				var f = b.trim(d).split(" ");
				c[0] = (f.length == 2) ? f : (f[0].match(/^(ASC|DESC)$/i)) ? [
						g, f[0]] : [f[0], "ASC"]
			}
			return c
		},
		_setMessage : function() {
			var c;
			switch (this.option.lang) {
				case "en" :
					c = {
						add_btn : "Add button",
						add_title : "add a box",
						del_btn : "Del button",
						del_title : "delete a box",
						next : "Next",
						next_title : "Next" + this.option.per_page
								+ " (Right key)",
						prev : "Prev",
						prev_title : "Prev" + this.option.per_page
								+ " (Left key)",
						first_title : "First (Shift + Left key)",
						last_title : "Last (Shift + Right key)",
						get_all_btn : "Get All (Down key)",
						get_all_alt : "(button)",
						close_btn : "Close (Tab key)",
						close_alt : "(button)",
						loading : "loading...",
						loading_alt : "(loading)",
						page_info : "num_page_top - num_page_end of cnt_whole",
						select_ng : "Attention : Please choose from among the list.",
						select_ok : "OK : Correctly selected.",
						not_found : "not found"
					};
					break;
				default :
					c = {
						add_btn : "Add button",
						add_title : "add a box",
						del_btn : "Del button",
						del_title : "delete a box",
						next : ">",
						next_title : "下" + this.option.per_page + "个 (→键)",
						prev : "<",
						prev_title : "上" + this.option.per_page + "个 (←键)",
						first_title : "第一页 (Shift + Left key)",
						last_title : "最后一页 (Shift + Right key)",
						get_all_btn : "",
						get_all_alt : "(button)",
						close_btn : "Close (Tab key)",
						close_alt : "(button)",
						loading : "loading...",
						loading_alt : "(loading)",
						page_info : "num_page_top - num_page_end of cnt_whole",
						select_ng : "Attention : Please choose from among the list.",
						select_ok : "OK : Correctly selected.",
						not_found : "没有匹配"
					}
			}
			this.message = c
		},
		_setCssClass : function() {
			var c = {
				container : "ac_container",
				container_open : "ac_container_open",
				selected : "ac_selected",
				re_area : "ac_result_area",
				navi : "ac_navi",
				results : "ac_results",
				re_off : "ac_results_off",
				select : "ac_over",
				sub_info : "ac_subinfo",
				select_ok : "ac_select_ok",
				select_ng : "ac_select_ng",
				input_off : "ac_input_off"
			};
			switch (this.option.plugin_type) {
				case "combobox" :
					c = b.extend(c, {
								button : "ac_button",
								btn_on : "ac_btn_on",
								btn_out : "ac_btn_out",
								input : "ac_input"
							});
					break;
				case "simple" :
					c = b.extend(c, {
								input : "ac_s_input"
							});
					break;
				case "textarea" :
					c = b.extend(c, {
								input : "ac_textarea",
								btn_short_off : "ac_btn_short_off"
							});
					break
			}
			this.css_class = c
		},
		_setProp : function() {
			this.prop = {
				timer_valchange : false,
				is_suggest : false,
				page_all : 1,
				page_suggest : 1,
				max_all : 1,
				max_suggest : 1,
				is_paging : false,
				is_loading : false,
				reserve_btn : false,
				reserve_click : false,
				xhr : false,
				key_paging : false,
				key_select : false,
				prev_value : "",
				size_navi : null,
				size_results : null,
				size_li : null,
				size_left : null,
				tag : null
			}
		},
		_setElem : function(d) {
			var e = {};
			e.combo_input = b(d).attr("autocomplete", "off")
					.addClass(this.css_class.input).wrap("<div>");
			e.container = b(e.combo_input).parent()
					.addClass(this.css_class.container);
			e.clear = b("<div>").css("clear", "left");
			if (this.option.plugin_type == "combobox") {
				e.button = b("<div>").addClass(this.css_class.button);
				e.img = b("<img>").attr("src", this.option.button_img)
			} else {
				e.button = false;
				e.img = false
			}
			e.result_area = b("<div>").addClass(this.css_class.re_area);
			e.navi = b("<div>").addClass(this.css_class.navi);
			e.navi_info = b("<div>").addClass("info");
			e.navi_p = b("<p>");
			e.results = b("<ul>").addClass(this.css_class.results);
			e.sub_info = b("<div>").addClass(this.css_class.sub_info);
			if (this.option.plugin_type == "textarea") {
				e.hidden = false
			} else {
				var g = (b(e.combo_input).attr("name") != undefined)
						? b(e.combo_input).attr("name")
						: b(e.combo_input).attr("id");
				if (g.match(/\]$/)) {
					g = g.replace(/\]?$/, "_primary_key]")
				} else {
					g += "_primary_key"
				}
				e.hidden = b("#" + g).attr({
							id : g
						})
			}
			switch (this.option.plugin_type) {
				case "combobox" :
					b(e.container).append(e.button).append(e.clear)
							.append(e.result_area).append(e.hidden);
					b(e.button).append(e.img);
					break;
				case "simple" :
					b(e.container).append(e.clear).append(e.result_area)
							.append(e.hidden);
					break;
				case "textarea" :
					b(e.container).append(e.clear).append(e.result_area)
			}
			b(e.result_area).append(e.navi).append(e.results)
					.append(e.sub_info);
			b(e.navi).append(e.navi_info).append(e.navi_p);
			if (this.option.plugin_type == "combobox") {
				if (b(e.combo_input).parents("td")) {
					var c = b(e.combo_input).parents("td").innerWidth();
					b(e.container).css("width", "100%");
					b(e.combo_input).width(b(e.container).innerWidth() - 10
							- 18)
				}
				var f = b(e.combo_input).outerWidth()
						+ b(e.button).outerWidth();
				b(e.button).height(b(e.combo_input).innerHeight())
			} else {
				b(e.container).width(b(e.combo_input).outerWidth())
			}
			this.elem = e
		},
		_setButtonAttrDefault : function() {
			if (this.option.select_only) {
				if (b(this.elem.combo_input).val() != "") {
					if (this.option.plugin_type != "textarea") {
						if (b(this.elem.hidden).val() != "") {
							b(this.elem.combo_input).attr("title",
									this.message.select_ok)
									.removeClass(this.css_class.select_ng)
									.addClass(this.css_class.select_ok)
						} else {
							b(this.elem.combo_input).attr("title",
									this.message.select_ng)
									.removeClass(this.css_class.select_ok)
									.addClass(this.css_class.select_ng)
						}
					}
				} else {
					if (this.option.plugin_type != "textarea") {
						b(this.elem.hidden).val("")
					}
					b(this.elem.combo_input).removeAttr("title")
							.removeClass(this.css_class.select_ng)
				}
			}
			if (this.option.plugin_type == "combobox") {
				b(this.elem.button).attr("title", this.message.get_all_btn);
				b(this.elem.img).attr("src", this.option.button_img)
			}
		},
		_setButtonPosition : function() {
			if (this.option.plugin_type != "combobox") {
				return
			}
			var d = b(this.elem.button).innerWidth();
			var h = b(this.elem.button).innerHeight();
			var c = b(this.elem.img).width();
			var e = b(this.elem.img).height();
			var g = d / 2 - (c / 2);
			var f = h / 2 - (e / 2);
			b(this.elem.img).css({
						top : f,
						left : g
					})
		},
		_setInitRecord : function() {
			if (this.option.init_record === false) {
				return
			}
			if (this.option.plugin_type != "textarea") {
				b(this.elem.hidden).val(this.option.init_record)
			}
			if (typeof this.option.source == "object") {
				for (var d = 0; d < this.option.source.length; d++) {
					if (this.option.source[d][this.option.primary_key] == this.option.init_record) {
						var e = this.option.source[d];
						break
					}
				}
				this._afterInit(this, e)
			} else {
				var c = this;
				b.getJSON(this.option.source, {
							db_table : this.option.db_table,
							pkey_name : this.option.primary_key,
							pkey_val : this.option.init_record
						}, function(f) {
							c._afterInit(c, f)
						})
			}
		},
		_afterInit : function(c, d) {
			b(c.elem.combo_input).val(d[c.option.field]);
			if (c.option.plugin_type != "textarea") {
				b(c.elem.hidden).val(d[c.option.primary_key])
			}
			c.prop.prev_value = d[c.option.field];
			if (c.option.select_only) {
				b(c.elem.combo_input).attr("title", c.message.select_ok)
						.removeClass(c.css_class.select_ng)
						.addClass(c.css_class.select_ok)
			}
		},
		_ehButton : function() {
			if (this.option.plugin_type != "combobox") {
				return
			}
			var c = this;
			b(c.elem.button).mouseup(function(d) {
						if (b(c.elem.result_area).is(":hidden")) {
							clearInterval(c.prop.timer_valchange);
							c.prop.is_suggest = false;
							c._suggest(c);
							b(c.elem.combo_input).focus()
						} else {
							c._hideResults(c)
						}
						d.stopPropagation()
					}).mouseover(function() {
				b(c.elem.button).addClass(c.css_class.btn_on)
						.removeClass(c.css_class.btn_out)
			}).mouseout(function() {
				b(c.elem.button).addClass(c.css_class.btn_out)
						.removeClass(c.css_class.btn_on)
			}).mouseout()
		},
		_ehComboInput : function() {
			var c = this;
			b(c.elem.combo_input).keydown(function(d) {
						c._processKey(c, d)
					});
			b(c.elem.combo_input).focus(function() {
						c._setTimerCheckValue(c)
					}).click(function() {
				c._setCssFocusedInput(c);
				b(c.elem.results).children("li")
						.removeClass(c.css_class.select);
				c.prop.is_suggest = true;
				c._suggest(c)
			})
		},
		_ehWhole : function() {
			var c = this;
			var d = false;
			b(c.elem.container).mousedown(function() {
						d = true
					});
			b("html").mousedown(function() {
						if (d) {
							d = false
						} else {
							c._hideResults(c)
						}
					})
		},
		_ehResults : function() {
			var c = this;
			b(c.elem.results).children("li").mouseover(function() {
				if (c.prop.key_select) {
					c.prop.key_select = false;
					return
				}
				c._setSubInfo(c, this);
				b(c.elem.results).children("li")
						.removeClass(c.css_class.select);
				b(this).addClass(c.css_class.select);
				c._setCssFocusedResults(c)
			}).click(function(d) {
						if (c.prop.key_select) {
							c.prop.key_select = false;
							return
						}
						d.preventDefault();
						d.stopPropagation();
						c._selectCurrentLine(c, false)
					})
		},
		_ehNaviPaging : function(c) {
			b(c.elem.navi).find(".navi_first").mouseup(function(d) {
						b(c.elem.combo_input).focus();
						d.preventDefault();
						c._firstPage(c)
					});
			b(c.elem.navi).find(".navi_prev").mouseup(function(d) {
						b(c.elem.combo_input).focus();
						d.preventDefault();
						c._prevPage(c)
					});
			b(c.elem.navi).find(".navi_page").mouseup(function(d) {
						b(c.elem.combo_input).focus();
						d.preventDefault();
						if (!c.prop.is_suggest) {
							c.prop.page_all = parseInt(b(this).text(), 10)
						} else {
							c.prop.page_suggest = parseInt(b(this).text(), 10)
						}
						c.prop.is_paging = true;
						c._suggest(c)
					});
			b(c.elem.navi).find(".navi_next").mouseup(function(d) {
						b(c.elem.combo_input).focus();
						d.preventDefault();
						c._nextPage(c)
					});
			b(c.elem.navi).find(".navi_last").mouseup(function(d) {
						b(c.elem.combo_input).focus();
						d.preventDefault();
						c._lastPage(c)
					})
		},
		_ehTextArea : function() {
			var c = this;
			if (!c.option.shorten_btn) {
				return
			}
			b(c.option.shorten_btn).click(function() {
						c._getShortURL(c)
					})
		},
		_getShortURL : function(d) {
			b(d.elem.combo_input).attr("disabled", "disabled");
			var h = b(d.elem.combo_input).val();
			var g = [];
			var c = null;
			while ((c = d.option.shorten_reg.exec(h)) != null) {
				g[g.length] = c[1]
			}
			if (g.length < 1) {
				b(d.elem.combo_input).removeAttr("disabled");
				return
			}
			var f = {};
			for (var e = 0; e < g.length; e++) {
				f["p_" + e] = g[e]
			}
			b.getJSON(d.option.shorten_src, f, function(l) {
						var k = 0;
						var j = h.replace(d.option.shorten_reg, function() {
									var m = arguments[0].replace(arguments[1],
											l[k]);
									k++;
									return m
								});
						b(d.elem.combo_input).val(j);
						d.prop.prev_value = j;
						b(d.elem.combo_input).focus();
						d._disableButtonShort(d);
						b(d.elem.combo_input).removeAttr("disabled")
					})
		},
		_scrollWindow : function(m, j) {
			var c = m._getCurrentLine(m);
			var l = (c && !j)
					? c.offset().top
					: b(m.elem.container).offset().top;
			var f;
			if (m.option.sub_info) {
				var e = b(m.elem.sub_info).children("dl:visible");
				f = b(e).height() + parseInt(b(e).css("border-top-width"))
						+ parseInt(b(e).css("border-bottom-width"))
			} else {
				m.prop.size_li = b(m.elem.results).children("li:first")
						.outerHeight();
				f = m.prop.size_li
			}
			var h = b(window).height();
			var g = b(window).scrollTop();
			var d = g + h - f;
			var k;
			if (b(c).length) {
				if (l < g || f > h) {
					k = l - g
				} else {
					if (l > d) {
						k = l - d
					} else {
						return
					}
				}
			} else {
				if (l < g) {
					k = l - g
				}
			}
			window.scrollBy(0, k)
		},
		_setCssFocusedInput : function(c) {
			b(c.elem.results).addClass(c.css_class.re_off);
			b(c.elem.combo_input).removeClass(c.css_class.input_off);
			b(c.elem.sub_info).children("dl").hide()
		},
		_setCssFocusedResults : function(c) {
			b(c.elem.results).removeClass(c.css_class.re_off);
			b(c.elem.combo_input).addClass(c.css_class.input_off)
		},
		_enableButtonShort : function(c) {
			b(c.option.shorten_btn).removeClass(c.css_class.btn_short_off)
					.removeAttr("disabled")
		},
		_disableButtonShort : function(c) {
			b(c.option.shorten_btn).addClass(c.css_class.btn_short_off).attr(
					"disabled", "disabled")
		},
		_setTimerCheckValue : function(c) {
			c.prop.timer_valchange = setTimeout(function() {
						c._checkValue(c)
					}, 500)
		},
		_checkValue : function(f) {
			var d = b(f.elem.combo_input).val();
			if (d != f.prop.prev_value) {
				f.prop.prev_value = d;
				if (f.option.plugin_type == "textarea") {
					f._findUrlToShorten(f);
					var c = f._findTag(f, d);
					if (c) {
						f._setTextAreaNewSearch(f, c);
						f._suggest(f)
					}
				} else {
					b(f.elem.combo_input).removeAttr("sub_info");
					if (f.option.plugin_type != "textarea") {
						b(f.elem.hidden).val("")
					}
					if (f.option.select_only) {
						f._setButtonAttrDefault()
					}
					f.prop.page_suggest = 1;
					f.prop.is_suggest = true;
					f._suggest(f)
				}
			} else {
				if (f.option.plugin_type == "textarea"
						&& b(f.elem.result_area).is(":visible")) {
					var e = f._findTag(f, d);
					if (!e) {
						f._hideResults(f)
					} else {
						if (e.str != f.prop.tag.str
								|| e.pos_left != f.prop.tag.pos_left) {
							f._setTextAreaNewSearch(f, e);
							f._suggest(f)
						}
					}
				}
			}
			f._setTimerCheckValue(f)
		},
		_setTextAreaNewSearch : function(d, c) {
			d.prop.tag = c;
			d.prop.page_suggest = 1;
			d.option.search_field = d.option.tags[d.prop.tag.type].search_field;
			d.option.order_by = d.option.tags[d.prop.tag.type].order_by;
			d.option.primary_key = d.option.tags[d.prop.tag.type].primary_key;
			d.option.db_table = d.option.tags[d.prop.tag.type].db_table;
			d.option.field = d.option.tags[d.prop.tag.type].field;
			d.option.sub_info = d.option.tags[d.prop.tag.type].sub_info;
			d.option.sub_as = d.option.tags[d.prop.tag.type].sub_as;
			d.option.show_field = d.option.tags[d.prop.tag.type].show_field;
			d.option.hide_field = d.option.tags[d.prop.tag.type].hide_field
		},
		_findUrlToShorten : function(e) {
			var d = null;
			var c = null;
			while ((c = e.option.shorten_reg.exec(b(e.elem.combo_input).val())) != null) {
				d = true;
				e.option.shorten_reg.lastIndex = 0;
				break
			}
			if (d) {
				e._enableButtonShort(e)
			} else {
				e._disableButtonShort(e)
			}
		},
		_findTag : function(l, c) {
			var h = l._getCaretPosition(b(l.elem.combo_input).get(0));
			for (var e = 0; e < l.option.tags.length; e++) {
				var d = c.substring(0, h);
				d = d.match(l.option.tags[e].pattern.reg_left);
				if (!d) {
					continue
				}
				d = d[1];
				var f = h - d.length;
				if (f < 0) {
					f = 0
				}
				var k = c.substring(h, c.length);
				k = k.match(l.option.tags[e].pattern.reg_right);
				if (k) {
					k = k[1];
					var j = h + k.length
				} else {
					k = "";
					var j = h
				}
				var g = d + "" + k;
				l.prop.is_suggest = (g == "") ? false : true;
				return {
					type : e,
					str : g,
					pos_left : f,
					pos_right : j
				}
			}
			return false
		},
		_getCaretPosition : function(d) {
			var e = 0;
			if (document.selection) {
				d.focus();
				var c = document.selection.createRange();
				c.moveStart("character", -d.value.length);
				e = c.text.length
			} else {
				if (d.selectionStart || d.selectionStart == "0") {
					e = d.selectionStart
				}
			}
			return e
		},
		_setCaretPosition : function(d, f) {
			var e = b(d.elem.combo_input).get(0);
			if (e.setSelectionRange) {
				e.focus();
				e.setSelectionRange(f, f)
			} else {
				if (e.createTextRange) {
					var c = e.createTextRange();
					c.collapse(true);
					c.moveEnd("character", f);
					c.moveStart("character", f);
					c.select()
				}
			}
		},
		_processKey : function(c, d) {
			if ((b.inArray(d.keyCode, [27, 38, 40, 9]) > -1 && b(c.elem.result_area)
					.is(":visible"))
					|| (b.inArray(d.keyCode, [37, 39, 13, 9]) > -1 && c
							._getCurrentLine(c))
					|| (d.keyCode == 40 && c.option.plugin_type != "textarea")) {
				d.preventDefault();
				d.stopPropagation();
				d.cancelBubble = true;
				d.returnValue = false;
				switch (d.keyCode) {
					case 37 :
						if (d.shiftKey) {
							c._firstPage(c)
						} else {
							c._prevPage(c)
						}
						break;
					case 38 :
						c.prop.key_select = true;
						c._prevLine(c);
						break;
					case 39 :
						if (d.shiftKey) {
							c._lastPage(c)
						} else {
							c._nextPage(c)
						}
						break;
					case 40 :
						if (b(c.elem.results).children("li").length) {
							c.prop.key_select = true;
							c._nextLine(c)
						} else {
							c.prop.is_suggest = false;
							c._suggest(c)
						}
						break;
					case 9 :
						c.prop.key_paging = true;
						c._hideResults(c);
						break;
					case 13 :
						c._selectCurrentLine(c, true);
						break;
					case 27 :
						c.prop.key_paging = true;
						c._hideResults(c);
						break
				}
			} else {
				if (d.keyCode != 16) {
					c._setCssFocusedInput(c)
				}
				c._checkValue(c)
			}
		},
		_abortAjax : function(c) {
			if (c.prop.xhr) {
				c.prop.xhr.abort();
				c.prop.xhr = false
			}
		},
		_suggest : function(d, h) {
			if (d.option.plugin_type != "textarea") {
				var e = (d.prop.is_suggest) ? b.trim(b(d.elem.combo_input)
						.val()) : ""
			} else {
				var e = [d.prop.tag.str]
			}
			d._abortAjax(d);
			d._setLoading(d);
			b(d.elem.sub_info).children("dl").hide();
			if (d.prop.is_paging) {
				var f = d._getCurrentLine(d);
				d.prop.is_paging = (f) ? b(d.elem.results).children("li")
						.index(f) : -1
			} else {
				if (!d.prop.is_suggest) {
					d.prop.is_paging = 0
				}
			}
			var c = (d.prop.is_suggest) ? d.prop.page_suggest : d.prop.page_all;
			var g = (b(d.elem.combo_input).attr("condition"))
					? b(d.elem.combo_input).attr("condition")
					: "";
			if (typeof d.option.source == "object") {
				d._searchForJson(d, e, c)
			} else {
				d._searchForDb(d, e, c, h, g)
			}
		},
		_setLoading : function(c) {
			b(c.elem.navi_info).text(c.message.loading);
			if (b(c.elem.results).html() == "") {
				b(c.elem.navi).children("p").empty();
				c._calcWidthResults(c);
				b(c.elem.container).addClass(c.css_class.container_open)
			}
		},
		_searchForDb : function(d, e, c, g, f) {
			d.prop.xhr = b.getJSON(d.option.source + "r=" + Math.random(), {
						q_word : e,
						type : d.option.type,
						condition : f,
						page_num : c,
						per_page : d.option.per_page
					}, function(j) {
						var h = j;
						j.candidate = [];
						j.primary_key = [];
						j.subinfo = [];
						if (typeof j.result != "object") {
							d.prop.xhr = null;
							d._notFoundSearch(d);
							return
						}
						if (j.result.length == 0) {
							d._hideResults(d);
							return
						}
						if (e.length != 0) {
							b(d.elem.hidden)
									.val(j.result[0][d.option.primary_key])
						}
						if (d.option.is_strict) {
							if (b(d.elem.combo_input).val() != j.result[0][d.option.field]) {
								b(d.elem.hidden).val("")
							}
						}
						if (j.result.length == 1) {
							if (b(d.elem.combo_input).val().length > j.result[0][d.option.field].length) {
								d._hideResults(d);
								return
							}
							if (g) {
								if (b(d.elem.combo_input).val().length == j.result[0][d.option.field].length) {
									d._hideResults(d);
									return
								}
							}
						}
						j.cnt_page = j.result.length;
						for (i = 0; i < j.cnt_page; i++) {
							j.subinfo[i] = [];
							for (key in j.result[i]) {
								if (key == d.option.primary_key) {
									j.primary_key.push(j.result[i][key])
								}
								if (key == d.option.field) {
									j.candidate.push(j.result[i][key])
								} else {
									if (b.inArray(key, d.option.hide_field) == -1) {
										if (d.option.show_field != ""
												&& b.inArray("*",
														d.option.show_field) == -1
												&& b.inArray(key,
														d.option.show_field) == -1) {
											continue
										} else {
											j.subinfo[i][key] = j.result[i][key]
										}
									}
								}
							}
						}
						delete(j.result);
						d.prop.xhr = null;
						d._prepareResults(d, j, e, c)
					})
		},
		_searchForJson : function(m, g, n) {
			var k = [];
			var d = [];
			var c = [];
			var t = {};
			var q = 0;
			var l = [];
			do {
				d[q] = g[q].replace(/\W/g, "\\$&").toString();
				l[q] = new RegExp(d[q], "gi");
				q++
			} while (q < g.length);
			for (var q = 0; q < m.option.source.length; q++) {
				var p = false;
				for (var o = 0; o < l.length; o++) {
					if (m.option.source[q][m.option.field].match(l[o])) {
						p = true;
						if (m.option.and_or == "OR") {
							break
						}
					} else {
						p = false;
						if (m.option.and_or == "AND") {
							break
						}
					}
				}
				if (p) {
					k.push(m.option.source[q])
				}
			}
			if (k.length == undefined) {
				m._notFoundSearch(m);
				return
			}
			t.cnt_whole = k.length;
			var w = new RegExp("^" + d[0] + "$", "gi");
			var u = new RegExp("^" + d[0], "gi");
			var v = [];
			var s = [];
			var r = [];
			for (var q = 0; q < k.length; q++) {
				if (k[q][m.option.order_by[0][0]].match(w)) {
					v.push(k[q])
				} else {
					if (k[q][m.option.order_by[0][0]].match(u)) {
						s.push(k[q])
					} else {
						r.push(k[q])
					}
				}
			}
			if (m.option.order_by[0][1].match(/^asc$/i)) {
				v = m._sortAsc(m, v);
				s = m._sortAsc(m, s);
				r = m._sortAsc(m, r)
			} else {
				v = m._sortDesc(m, v);
				s = m._sortDesc(m, s);
				r = m._sortDesc(m, r)
			}
			c = c.concat(v).concat(s).concat(r);
			var f = (n - 1) * m.option.per_page;
			var e = f + m.option.per_page;
			for (var q = f, h = 0; q < e; q++, h++) {
				if (c[q] == undefined) {
					break
				}
				for (var x in c[q]) {
					if (x == m.option.primary_key) {
						if (t.primary_key == undefined) {
							t.primary_key = []
						}
						t.primary_key.push(c[q][x])
					}
					if (x == m.option.field) {
						if (t.candidate == undefined) {
							t.candidate = []
						}
						t.candidate.push(c[q][x])
					} else {
						if (b.inArray(x, m.option.hide_field) == -1) {
							if (m.option.show_field != ""
									&& b.inArray("*", m.option.show_field) == -1
									&& b.inArray(x, m.option.show_field) == -1) {
								continue
							}
							if (t.subinfo == undefined) {
								t.subinfo = []
							}
							if (t.subinfo[h] == undefined) {
								t.subinfo[h] = []
							}
							t.subinfo[h][x] = c[q][x]
						}
					}
				}
			}
			t.cnt_page = t.candidate.length;
			m._prepareResults(m, t, g, n)
		},
		_sortAsc : function(d, c) {
			c.sort(function(f, e) {
						return f[d.option.order_by[0][0]]
								.localeCompare(e[d.option.order_by[0][0]])
					});
			return c
		},
		_sortDesc : function(d, c) {
			c.sort(function(f, e) {
						return e[d.option.order_by[0][0]]
								.localeCompare(f[d.option.order_by[0][0]])
					});
			return c
		},
		_notFoundSearch : function(c) {
			b(c.elem.navi_info).text(c.message.not_found);
			b(c.elem.navi_p).hide();
			b(c.elem.results).empty();
			b(c.elem.sub_info).empty();
			c._calcWidthResults(c);
			b(c.elem.container).addClass(c.css_class.container_open);
			c._setCssFocusedInput(c)
		},
		_prepareResults : function(f, g, h, d) {
			f._setNavi(f, g.cnt_whole, g.cnt_page, d);
			if (!g.subinfo || !f.option.sub_info) {
				g.subinfo = false
			}
			if (!g.primary_key) {
				g.primary_key = false
			}
			if (f.option.select_only && g.candidate.length === 1
					&& g.candidate[0] == h[0]) {
				if (f.option.plugin_type != "textarea") {
					b(f.elem.hidden).val(g.primary_key[0])
				}
				this._setButtonAttrDefault()
			}
			f._displayResults(f, g.candidate, g.subinfo, g.primary_key);
			if (f.prop.is_paging === false) {
				f._setCssFocusedInput(f)
			} else {
				var c = f.prop.is_paging;
				var e = b(f.elem.results).children("li").length - 1;
				if (c > e) {
					c = e
				}
				var j = b(f.elem.results).children("li").eq(c);
				b(j).addClass(f.css_class.select);
				f._setSubInfo(f, j);
				f.prop.is_paging = false;
				f._setCssFocusedResults(f)
			}
		},
		_setNavi : function(n, l, m, c) {
			var o = n.option.per_page * (c - 1) + 1;
			var h = o + m - 1;
			var d = n.message.page_info.replace("cnt_whole", l).replace(
					"num_page_top", o).replace("num_page_end", h);
			b(n.elem.navi_info).text(d);
			var g = Math.ceil(l / n.option.per_page);
			if (g > 1) {
				b(n.elem.navi_p).empty();
				if (n.prop.is_suggest) {
					n.prop.max_suggest = g
				} else {
					n.prop.max_all = g
				}
				var e = c - Math.ceil((n.option.navi_num - 1) / 2);
				var k = c + Math.floor((n.option.navi_num - 1) / 2);
				while (e < 1) {
					e++;
					k++
				}
				while (k > g) {
					k--
				}
				while ((k - e < n.option.navi_num - 1) && e > 1) {
					e--
				}
				if (c == 1) {
					if (!n.option.navi_simple) {
						b("<span>").text("<< 1").addClass("page_end")
								.appendTo(n.elem.navi_p)
					}
					b("<span>").text(n.message.prev).addClass("page_end")
							.appendTo(n.elem.navi_p)
				} else {
					if (!n.option.navi_simple) {
						b("<a>").attr({
									href : "javascript:void(0)",
									"class" : "navi_first"
								}).text("<< 1").attr("title",
								n.message.first_title).appendTo(n.elem.navi_p)
					}
					b("<a>").attr({
								href : "javascript:void(0)",
								"class" : "navi_prev",
								title : n.message.prev_title
							}).text(n.message.prev).appendTo(n.elem.navi_p)
				}
				for (var f = e; f <= k; f++) {
					var j = (f == c)
							? '<span class="current">' + f + "</span>"
							: f;
					b("<a>").attr({
								href : "javascript:void(0)",
								"class" : "navi_page"
							}).html(j).appendTo(n.elem.navi_p)
				}
				if (c == g) {
					b("<span>").text(n.message.next).addClass("page_end")
							.appendTo(n.elem.navi_p);
					if (!n.option.navi_simple) {
						b("<span>").text(g + " >>").addClass("page_end")
								.appendTo(n.elem.navi_p)
					}
				} else {
					b("<a>").attr({
								href : "javascript:void(0)",
								"class" : "navi_next"
							}).text(n.message.next).attr("title",
							n.message.next_title).appendTo(n.elem.navi_p);
					if (!n.option.navi_simple) {
						b("<a>").attr({
									href : "javascript:void(0)",
									"class" : "navi_last"
								}).text(g + " >>").attr("title",
								n.message.last_title).appendTo(n.elem.navi_p)
					}
				}
				b(n.elem.navi_p).show();
				n._ehNaviPaging(n)
			} else {
				b(n.elem.navi_p).hide()
			}
		},
		_setSubInfo : function(f, g) {
			if (!f.option.sub_info) {
				return
			}
			f.prop.size_results = (b(f.elem.results).outerHeight() - b(f.elem.results)
					.height())
					/ 2;
			f.prop.size_navi = b(f.elem.navi).outerHeight();
			f.prop.size_li = b(f.elem.results).children("li:first")
					.outerHeight();
			f.prop.size_left = b(f.elem.results).outerWidth();
			var c = b(f.elem.results).children("li").index(g);
			b(f.elem.sub_info).children("dl").hide();
			var d = 0;
			if (b(f.elem.navi).css("display") != "none") {
				d += f.prop.size_navi
			}
			d += (f.prop.size_results + f.prop.size_li * c);
			var e = f.prop.size_left;
			d += "px";
			e += "px";
			b(f.elem.sub_info).children("dl").eq(c).css({
						position : "absolute",
						top : d,
						left : e,
						display : "block"
					})
		},
		_displayResults : function(o, n, j, k) {
			b(o.elem.results).empty();
			b(o.elem.sub_info).empty();
			for (var g = 0; g < n.length; g++) {
				var h = b("<li>").text(n[g]).attr({
							pkey : k[g],
							title : n[g]
						});
				if (o.option.plugin_type != "textarea"
						&& k[g] == b(o.elem.hidden).val()) {
					b(h).addClass(o.css_class.selected)
				}
				b(o.elem.results).append(h);
				if (j) {
					var l = [];
					var e = b("<dl>");
					for (key in j[g]) {
						var c = key.replace("'", "\\'");
						if (j[g][key] == null) {
							j[g][key] = ""
						} else {
							j[g][key] += ""
						}
						var f = j[g][key].replace("'", "\\'");
						l.push("'" + c + "':'" + f + "'");
						if (o.option.sub_as[key] != null) {
							var d = o.option.sub_as[key]
						} else {
							var d = key
						}
						d = b("<dt>").text(d);
						if (o.option.sub_info == "simple") {
							b(d).addClass("hide")
						}
						e.append(d);
						var m = b("<dd>").text(j[g][key]);
						e.append(m)
					}
					l = "{" + l.join(",") + "}";
					b(h).attr("sub_info", l);
					b(o.elem.sub_info).append(e);
					if (o.option.sub_info == "simple"
							&& e.children("dd").text() == "") {
						e.addClass("ac_dl_empty")
					}
				}
			}
			o._calcWidthResults(o);
			b(o.elem.container).addClass(o.css_class.container_open);
			o._ehResults();
			if (o.option.plugin_type == "combobox") {
				b(o.elem.button).attr("title", o.message.close_btn)
			}
		},
		_calcWidthResults : function(d) {
			if (d.option.plugin_type == "combobox") {
				var c = b(d.elem.combo_input).outerWidth()
						+ b(d.elem.button).outerWidth()
			} else {
				var c = b(d.elem.combo_input).outerWidth()
			}
			if (b(d.elem.container).css("position") == "static") {
				var e = b(d.elem.combo_input).offset();
				b(d.elem.result_area).css({
							top : e.top + b(d.elem.combo_input).outerHeight()
									+ "px",
							left : e.left + "px"
						})
			} else {
				b(d.elem.result_area).css({
							top : b(d.elem.combo_input).outerHeight() + "px",
							left : "0px"
						})
			}
			b(d.elem.result_area)
					.width(b(d.elem.container).width()
							- (b(d.elem.result_area).outerWidth() - b(d.elem.result_area)
									.innerWidth()) - 8).show()
		},
		_hideResults : function(c) {
			if (c.prop.key_paging) {
				c._scrollWindow(c, true);
				c.prop.key_paging = false
			}
			c._setCssFocusedInput(c);
			b(c.elem.results).empty();
			b(c.elem.sub_info).empty();
			b(c.elem.result_area).hide();
			b(c.elem.container).removeClass(c.css_class.container_open);
			c._abortAjax(c);
			c._setButtonAttrDefault()
		},
		_firstPage : function(c) {
			if (!c.prop.is_suggest) {
				if (c.prop.page_all > 1) {
					c.prop.page_all = 1;
					c.prop.is_paging = true;
					c._suggest(c)
				}
			} else {
				if (c.prop.page_suggest > 1) {
					c.prop.page_suggest = 1;
					c.prop.is_paging = true;
					c._suggest(c)
				}
			}
		},
		_prevPage : function(c) {
			if (!c.prop.is_suggest) {
				if (c.prop.page_all > 1) {
					c.prop.page_all--;
					c.prop.is_paging = true;
					c._suggest(c)
				}
			} else {
				if (c.prop.page_suggest > 1) {
					c.prop.page_suggest--;
					c.prop.is_paging = true;
					c._suggest(c)
				}
			}
		},
		_nextPage : function(c) {
			if (c.prop.is_suggest) {
				if (c.prop.page_suggest < c.prop.max_suggest) {
					c.prop.page_suggest++;
					c.prop.is_paging = true;
					c._suggest(c)
				}
			} else {
				if (c.prop.page_all < c.prop.max_all) {
					c.prop.page_all++;
					c.prop.is_paging = true;
					c._suggest(c)
				}
			}
		},
		_lastPage : function(c) {
			if (!c.prop.is_suggest) {
				if (c.prop.page_all < c.prop.max_all) {
					c.prop.page_all = c.prop.max_all;
					c.prop.is_paging = true;
					c._suggest(c)
				}
			} else {
				if (c.prop.page_suggest < c.prop.max_suggest) {
					c.prop.page_suggest = c.prop.max_suggest;
					c.prop.is_paging = true;
					c._suggest(c)
				}
			}
		},
		_selectCurrentLine : function(l, j) {
			l._scrollWindow(l, true);
			var g = l._getCurrentLine(l);
			if (g) {
				if (l.option.plugin_type != "textarea") {
					b(l.elem.combo_input).val(b(g).text());
					if (l.option.sub_info) {
						b(l.elem.combo_input).attr("sub_info",
								b(g).attr("sub_info"))
					}
					if (l.option.select_only) {
						l._setButtonAttrDefault()
					}
					b(l.elem.hidden).val(b(g).attr("pkey"))
				} else {
					var d = l.prop.prev_value.substring(0, l.prop.tag.pos_left);
					var k = l.prop.prev_value.substring(l.prop.tag.pos_right);
					var e = b(g).text();
					if (l.option.tags[l.prop.tag.type].space[0]
							&& !d
									.match(l.option.tags[l.prop.tag.type].pattern.space_left)) {
						var c = l.option.tags[l.prop.tag.type].pattern.left.length;
						var f = d.length;
						d = d.substring(0, (f - c)) + " "
								+ d.substring((f - c))
					}
					if (!k
							.match(l.option.tags[l.prop.tag.type].pattern.comp_right)) {
						k = l.option.tags[l.prop.tag.type].pattern.right + k
					}
					if (l.option.tags[l.prop.tag.type].space[1]
							&& !k
									.match(l.option.tags[l.prop.tag.type].pattern.space_right)) {
						var c = l.option.tags[l.prop.tag.type].pattern.right.length;
						k = k.substring(0, c) + " " + k.substring(c)
					}
					b(l.elem.combo_input).val(d + "" + e + "" + k);
					var h = d.length + e.length;
					l._setCaretPosition(l, h)
				}
				l.prop.prev_value = b(l.elem.combo_input).val();
				l.prop.is_suggest = true;
				l.prop.page_suggest = 1;
				l._suggest(l, true)
			}
			if (l.option.bind_to) {
				b(l.elem.combo_input).trigger(l.option.bind_to, j)
			}
			b(l.elem.combo_input).focus();
			b(l.elem.combo_input).change();
			l._setCssFocusedInput(l)
		},
		_getCurrentLine : function(c) {
			if (b(c.elem.result_area).is(":hidden")) {
				return false
			}
			var d = b(c.elem.results).children("li." + c.css_class.select);
			if (b(d).length) {
				return d
			} else {
				return false
			}
		},
		_nextLine : function(d) {
			var f = d._getCurrentLine(d);
			if (!f) {
				var c = -1
			} else {
				var c = b(d.elem.results).children("li").index(f);
				b(f).removeClass(d.css_class.select)
			}
			c++;
			if (c < b(d.elem.results).children("li").length) {
				var e = b(d.elem.results).children("li").eq(c);
				d._setSubInfo(d, e);
				b(e).addClass(d.css_class.select);
				d._setCssFocusedResults(d)
			} else {
				d._setCssFocusedInput(d)
			}
			d._scrollWindow(d, false)
		},
		_prevLine : function(d) {
			var f = d._getCurrentLine(d);
			if (!f) {
				var c = b(d.elem.results).children("li").length
			} else {
				var c = b(d.elem.results).children("li").index(f);
				b(f).removeClass(d.css_class.select)
			}
			c--;
			if (c > -1) {
				var e = b(d.elem.results).children("li").eq(c);
				d._setSubInfo(d, e);
				b(e).addClass(d.css_class.select);
				d._setCssFocusedResults(d)
			} else {
				d._setCssFocusedInput(d)
			}
			d._scrollWindow(d, false)
		}
	})
})(jQuery);