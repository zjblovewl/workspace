/* 页面跳转
  调用方法：window.changePage(lPage, rPage, direction, callback);
  param:
    lPage:  跳转页面的左页面
    rPage:  跳转页面的右页面
    direction:(to-left/to-right)
              to-left: 向左侧跳转
              to-right: 向右侧跳转
    callback: 跳转结束后的执行的函数

  页面加载
  调用方法： $(当前页面).goToPage(url, callback);
  param:
    url: 目标页面地址
    callback: 跳转结束后的执行的函数
*/
;(function() {
  'use strict';
  var dom;
  var allowPageChange = true;
  var allowBack = true;
	//history.replaceState({'url': 'index'}, null, 'index');
  var changePage = function(lPage, rPage, direction, callback) {
    if (allowPageChange) {
      animatePages(lPage, rPage, direction);
      animationEnd(dom, function() {
        afterAniamtion(lPage, rPage, direction);
        if (callback) {
          callback();
        }
      });
    }
  };
  var animatePages = function(lPage, rPage, direction) {
    allowPageChange = false;
    var removeClasses = 'page-on-center page-on-left page-on-right';
    if (direction === 'to-left') {
      lPage.removeClass(removeClasses).addClass('page-from-center-to-left');
      rPage.removeClass(removeClasses).addClass('page-from-right-to-center');
      dom = rPage;
    }
    if (direction === 'to-right') {
      lPage.removeClass(removeClasses).addClass('page-from-left-to-center');
      rPage.removeClass(removeClasses).addClass('page-from-center-to-right');
      dom = lPage;
    }
  };
  var animationEnd = function(dom, callback) {
    var events = ['webkitAnimationEnd', 'animationEnd'],
      i, j;
    function fireCallback(e) {
      callback(e);
      for (j = 0; j < events.length; j++) {
        dom.off(events[i], fireCallback);
      }
    }
    if (callback) {
      for (i = 0; i < events.length; i++) {
        dom.on(events[i], fireCallback);
      }
    }
  };
  var afterAniamtion = function(lPage, rPage, direction) {
    if (direction === 'to-left') {
      lPage.removeClass('page-from-center-to-left page-on-center page-on-right').addClass('page-on-left');
      rPage.removeClass('page-from-right-to-center page-on-right page-on-left').addClass('page-on-center');
    }
    if (direction === 'to-right') {
      lPage.removeClass('page-from-left-to-center page-on-left page-on-right').addClass('page-on-center');
      rPage.removeClass('page-from-center-to-right page-on-center page-on-left').addClass('page-on-right');
      rPage.remove();
    }
    allowPageChange = true;
  };
  $.fn.goToPage = function(url, callback) {
    var curr_page = $(this);
    if (!curr_page.hasClass('page-on-center')) {
      curr_page = curr_page.parents('.page-on-center');
    }
    if (curr_page.next('.page').length == 0) {
      curr_page.after('<article class="page page-on-right"></article>');
    };
    var next_page = curr_page.next();
    next_page.load(url, function() {
      changePage(curr_page, next_page, 'to-left', function() {
        if (callback) {
          callback();
        }
        history.pushState({'url': url}, null, url);
        allowBack = true;
      });
    });
  };
  //返回动作监听事件
  window.onpopstate = function(e) {
    var state = e.state,
        curr_page = $('.pageNoHeader:last').length == 0 ? $('.page:last') : $('.pageNoHeader:last');
    if (e.state) {
      //页面从左往右（模拟返回动画）
      changePage(curr_page.prev(), curr_page, 'to-right', function() {
        curr_page.remove();
        allowBack = true;
      });
    } else {
      allowBack = true;
    }
  };
  //返回监听事件
  $('body').on('click', '.header-back', function() {
    history.go(-1);
    //historyBack();
  });
  //重写系统返回事件
  document.addEventListener('deviceready', function() {
    document.addEventListener('backbutton', function() {
      historyBack();
    }, false);
  }, false);
  //返回到上一个历史
//var historyBack = function() {
//  if (allowBack) {
//    //平台页面不允许返回上一个历史页面
//    if (history.state.url !== '../html/platform.html') {
//      allowBack = false;
//      history.go(-1);
//    }
//  }
//};
})();


