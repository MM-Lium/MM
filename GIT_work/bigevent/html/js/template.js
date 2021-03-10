
/* loading func -----------------------------*/
var loading = function(){
    
    var $this = {};
    $this.callback = function(){}
    $this.removeLoading = function(){
        $('.loading').fadeOut(function(){
            $('.loading').remove();
            
            $this.callback();
            // 動態程式寫這
            // script();
            
        });
    };
    $this.loadfunc = function(){
        $('html, body').imagesLoaded(function(){
            $this.removeLoading();
        });
    };
    return $this;
};


