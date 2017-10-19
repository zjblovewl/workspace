//projectName: dialogEs
//author: sunHaiTao
//version: 2017-04-27 ver1.1

//默认弹窗
//var dialog1 = new DialogEs('用户名密码错误！');
//带参数弹窗
//    var option = {
//        width: '',
//        height: '',
//        unit: '',
//        ok:function () {
//            console.log('我是确定')
//        },
//        cancel:function () {
//            console.log('我是取消')
//        },
//        cancelShow: false //是否显示取消按钮
//    };
//    var dialog1 = new DialogEs('用户名密码错误！', option);

function DialogEs(content,obj) {
    if(obj === null || typeof obj !== 'object'){
        obj = {};
    }
    if(content === null || typeof content !== 'string'){
        content = '';
    }
    if(typeof obj.content !== 'undefined'){
        content = obj.content;
    }
    this.id = 'dialog' + new Date().getTime() + Math.ceil(Math.random()*35);
    this.width = obj.width || 5;
    this.height = obj.height || 'auto';
    this.unit = obj.unit || 'rem';
    this.okTxt = obj.okTxt || '确定';
    this.cancelTxt = obj.cancelTxt || '取消';
    this.title = obj.title|| '';
    this.content = content || '';
    this.ok = obj.ok || function () {};
    this.cancel = obj.cancel || function () {};
    this.cancelShow = obj.cancelShow;
    this.okHide = obj.okHide;
    this.maskOffClick = obj.maskOffClick || false;

    var finalHeight,finalMarginTop;
    if(!parseInt(this.height)){
        finalHeight = 'auto';
        finalMarginTop = '-25%';
    }else{
        finalHeight = this.height+this.unit;
        finalMarginTop = -(this.height/2)+this.unit;
    }

    this.maskStyle = 'position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: 100; background-color: hsla(0,0%,0%,.3); ';
    this.style = 'position: absolute; left: 50%; top: 50%; background-color: #f1f5f7; ';
    this.style += 'width:'+ this.width+this.unit +'; height:'+ finalHeight +'; margin-left:'+ -(this.width/2)+this.unit + '; margin-top:'+ finalMarginTop +'; ';
    this.hdStyle = 'text-align:center; padding: .15rem; font-size:.4rem; ';
    this.ftStyle = 'display: flex; justify-content: space-around; display: -webkit-flex; -webkit-justify-content: space-around; padding:.15rem 0 .25rem; ';
    this.bdStyle = 'text-align:center; font-size:.32rem; ';
    this.btnStyle = '';
    if(obj.style){
        this.style += obj.style;
    }
    if(obj.hdStyle){
        this.hdStyle += obj.hdStyle;
    }
    if(obj.bdStyle){
        this.bdStyle += obj.bdStyle;
    }
    if(obj.ftStyle){
        this.ftStyle += obj.ftStyle;
    }
    if(obj.maskStyle){
        this.maskStyle += obj.maskStyle;
    }
    if(obj.btnStyle){
        this.btnStyle += obj.btnStyle;
    }

    this.render();

}
DialogEs.prototype.render = function () {
    var cancelHtmlString = '', okHtmlString = '',maskHtmlString = '';
    if(this.cancelShow){
        cancelHtmlString = '<a class="btn-cancel" onclick=\'('+ this.cancel +')()\' href="javascript:;">'+this.cancelTxt+'</a>';
    }
    if(!this.okHide){
        okHtmlString = '<a class="btn-confirm" style="'+ this.btnStyle +'" onclick=\'('+ this.ok +')()\' href="javascript:;">'+this.okTxt+'</a>';
    }
    if(this.maskOffClick){
        maskHtmlString = ' mask-off-click';
    }

    var htmlConstructor = '' +
        '<div id="'+ this.id +'" class="dialog-especial-mask '+ maskHtmlString +'" style="'+ this.maskStyle +'">\
                <div class="dialog-especial" style="'+ this.style +'">\
                    <div class="dialog-especial-hd" style="'+ this.hdStyle +'">\
                        '+ this.title +'\
                    </div>\
                    <div class="dialog-especial-bd" style="'+ this.bdStyle +'">\
                        '+ this.content +'\
                    </div>\
                    <div class="dialog-especial-ft" style="'+ this.ftStyle +'">\
                        '+ okHtmlString +'\
                        '+ cancelHtmlString +'\
                    </div>\
                </div>\
            </div>';

    $('body').append(htmlConstructor).on('click',function (e) {
        var $target = $(e.target);
        if($target.hasClass('btn-confirm') || $target.hasClass('btn-cancel')){
            $target.parents('.dialog-especial-mask').hide();
        }else if($target.hasClass('dialog-especial-mask') && !$target.hasClass('mask-off-click')){
            $target.hide();
        }
    });
    //update margin height
    if(!parseInt(this.height)){
        this.amendPosition();
    }
};

DialogEs.prototype.hide = function () {
    $('#'+ this.id).hide();
};
DialogEs.prototype.show = function () {
    $('#'+ this.id).show();
    this.amendPosition();
};
DialogEs.prototype.destroy = function () {
    $('#'+ this.id).remove();
};
DialogEs.prototype.alert = function (x) {
    $('#'+ this.id).find('.dialog-especial-bd').text(x);
    this.show();
};
DialogEs.prototype.amendPosition = function () {
    var $dialogEspecial = $('#'+ this.id).find('.dialog-especial');
    $dialogEspecial.css({
        'margin-top': -($dialogEspecial.height()/2)
    });
};