layui.use(["element", "layer"],function() {
	var i = layui.jquery,a = layui.element();
	i(window).on("resize",function() {
		var a = i("#tabContainers");
		a.height(i(this).height()),
		a.find("iframe").each(function() {
			i(this).height(a.height())
		});
	}).resize();
	var t = i("#tabTitle"),e = (i("#tabContainer"), i("#sideNav"));
	i(".layui-nav .layui-nav-item .layui-nav-child dd> a").each(function() {
		var l = i(this),n = l.data("url");
		void 0 !== n && l.on("click",function() {
			var d = l.html(),h = 0,s = t.find("li:last-child").attr("lay-id");
			if (t.find("li").each(function(a, t) {
				i(this).find("label").text() === l.text() && (h++, s = i(this).attr("lay-id"))
			}), 0 === h) {
				d += '<i class="layui-icon layui-unselect layui-tab-close">&#x1006;</i>',
				a.tabAdd("page-tab", {
					title: d,
					content: '<iframe src="' + n + '"></iframe>',
					id: s + 1
				}),
				s = t.find("li:last-child").attr("lay-id");
				var c = i(".layui-tab-content");
				c.find("iframe").each(function() {
					i(this).height(c.height())
				});
				var u = t.find("li");
				u.find("label").on("click",function() {
					var a = i(this).text(),t = e.find("dd.layui-this").find("a").text();
					a != t && (e.find("dd").each(function() {
						i(this).find("label").text() == a && (e.find(".layui-nav-child >dd").removeClass("layui-this"), i(this).addClass("layui-this"))
					}), "首页" == a && e.find(".layui-nav-child >dd").removeClass("layui-this"))
				}),
				u.eq(u.length - 1).children("i.layui-tab-close").on("click",function() {
					a.tabDelete("page-tab", s);
					var l = t.find("li.layui-this").find("label").text(),
					n = e.find("dd.layui-this").find("a").text();
					l != n && (e.find("dd").each(function() {
						i(this).find("label").text() == l && (e.find(".layui-nav-child >dd").removeClass("layui-this"), i(this).addClass("layui-this"))
					}), "首页" == l && e.find(".layui-nav-child >dd").removeClass("layui-this"))
				}),
				a.tabChange("page-tab", s)
			} else a.tabChange("page-tab", s)
		})
	})
});