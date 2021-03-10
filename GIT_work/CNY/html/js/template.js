
/* loading func -----------------------------*/
var loading = function(){
    
    var $this = {};
    $this.callback = script;
    $this.removeLoading = function(){
        $('html, body').scrollTop(0);
        $('.loading').fadeOut(400, function(){
            $('.loading').remove();
            $this.callback();
        });
    };
    $this.loadfunc = function(){
        var counter = 0;
        var $loadingBar = $('.loadingBar');
        $('html, body').imagesLoaded()
          .progress( function( instance, image ) {
            counter += 1;
            var prog = counter / instance.images.length * 100;
            $loadingBar.css({ width: prog+'%' })
          });
    };
    return $this;
};

