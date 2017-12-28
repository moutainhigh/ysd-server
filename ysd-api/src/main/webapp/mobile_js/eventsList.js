	//当前加载页  用来控制全局的上拉加载  下拉刷新
	var tag=1;
	var EventsList = function(element, options) {
		var $main = $('#wrapper');
		var $list = $main.find('#events-list');
		var $pullDown = $main.find('#pull-down');
		var $pullDownLabel = $main.find('#pull-down-label');
		var $pullUp = $main.find('#pull-up');
		var topOffset = -$pullDown.outerHeight();

		this.compiler = Handlebars.compile($('#tpi-list-item').html());
		tag= options.params.pageNumber;
		this.pageCount = null;

		this.getURL = function(params) {
			var queries = ['callback=1'];
			for (var key in  params) {
				if (key !== 'pageNumber') {
					queries.push(key + '=' + params[key]);
				}
			}
			queries.push('pageNumber=');
			return options.api + '?' + queries.join('&');
		};

		this.renderList = function(type) {
			var _this = this;
			var $el = $pullDown;

			if (type === 'load') {
				$el = $pullUp;
			}

/*                console.log("start");
			console.log(this.URL + tag);*/

			$.get(this.URL+tag).then(function(strdata) {


				var data= $.parseJSON(strdata);
/*                    console.log("data:");
				console.log(data);*/
				var datalist=eval('data.' + options.list);
				//替换掉默认的

				_this.pageCount = data.pageBean.pageCount;  //设置统计总页数的参数
				if (_this.pageCount == 1 || _this.pageCount == 0) {
					$("#pull-down").hide();
					$("#pull-up").html("没有更多数据哦 <span style='text-decoration:underline;' onclick='location.reload();'>点我刷新<span>");
				}
				
				tag =++data.pageBean.pageNumber; //请求成功了，pageNumber 自增 1
				var html = _this.compiler(datalist);
				if (type === 'refresh') {
					$list.html(html);
				} else if (type === 'load') {
					$list.append(html);
				} else {
					$list.html(html);
				}

				// refresh iScroll
				setTimeout(function() {
					_this.iScroll.refresh();
				}, 100);
			}, function() {
				console.log('Error...');
			}).always(function() {
				_this.resetLoading($el);
				if (type !== 'load') {
					_this.iScroll.scrollTo(0, topOffset, 800, $.AMUI.iScroll.utils.circular);
				}
			});
		};

		this.setLoading = function($el) {
			$el.addClass('loading');
		};

		this.resetLoading = function($el) {
			$el.removeClass('loading');
		};

		this.init = function() {
			var myScroll = this.iScroll = new $.AMUI.iScroll('#wrapper', {
				click: true
			});
			// myScroll.scrollTo(0, topOffset);
			var _this = this;
			var pullFormTop = false;
			var pullStart;

			this.URL = this.getURL(options.params);
			this.renderList();

			myScroll.on('scrollStart', function() {
				if (this.y >= topOffset) {
					pullFormTop = true;
				}

				pullStart = this.y;
				// console.log(this);
			});

			myScroll.on('scrollEnd', function() {
				if (pullFormTop && this.directionY === -1) {
					_this.handlePullDown();
				}
				pullFormTop = false;

				// pull up to load more
				if (pullStart === this.y && (this.directionY === 1)) {
					_this.handlePullUp();
				}
			});
		};

		this.handlePullDown = function() {
			console.log('handle pull down');
			//下拉刷新 先把  tag 的值设置为 1
			tag=1;
			this.setLoading($pullDown);
			this.renderList( 'refresh');
		};

		this.handlePullUp = function() {
//                console.log('handle pull up');
/*                console.log(this.next);
			console.log(this.total);*/
		
			if (tag <= this.pageCount) {
				this.setLoading($pullUp);
				this.renderList( 'load');
			} else {
				$("#pull-up").html("没有更多数据哦");
				// this.iScroll.scrollTo(0, topOffset);
			}
		}
	};

	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	}, false);
	
