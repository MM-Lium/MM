var script = function() {

    var $body = $('html,body');
    var winH = $(window).height();
    var winW = $(window).outerWidth();
    // var docH  = $(document).height();
    var $win = $(window);
    var scrollBottom = $(window).scrollTop() + $(window).height();
    var conH = $('.wrapper').height();
    var headerH = $('.myheader2').height();



    var $sec = $('.sec');
    var hash = location.hash,
        href;

    /* page scroll */
    var thispage = 0;
    var pagescroll = true;
    var pageT = [],
        $sec = $('.sec'),
        $main = $('.wrapper'),
        // _mainTOP = $main.offset().top,
        ah = $main.outerHeight(true),
        pageName = ['kv', 'z-series', 'n20-s20', 's20fe', 'tab-a7-s7', 'wa', 'video', 'moresp', 'buy'];

    var _thispagescroll = pageName.indexOf(hash);



    // if (winW > 768) {
    //     $('.playlistWrap').mCustomScrollbar({
    //         scrollInertia: 500,
    //         autoDraggerLength: false,
    //         axis: "y"
    //     });
    // } else {
    //     $('.playlistWrap').mCustomScrollbar({
    //         scrollInertia: 500,
    //         autoDraggerLength: false,
    //         axis: "x",

    //         // contentTouchScroll: integer
    //     });
    // }



    AOS.init({
        offset: 0,
        duration: 600,
        easing: 'ease-in-out-sine',
    });


    /* slick ---------------------------------------------------*/

    // $('.sec7 .playlist').slick({
    //     slidesToShow: 3,
    //     slidesToScroll: 1,
    //     infinite: true,
    //     arrows: true,
    //     autoplaySpeed: 3500,
    //     centerMode: true,
    //     centerPadding: '40px',
    //     speed: 300,
    //     responsive: [{
    //             breakpoint: 1024,
    //             settings: {
    //                 slidesToShow: 2
    //             }
    //         },
    //         {
    //             breakpoint: 640,
    //             settings: {
    //                 slidesToShow: 1
    //             }
    //         }
    //     ]
    // });


    if (winW > 768) {
        var swiper2 = new Swiper('.gallery-txt', {
            effect: 'slide', //滑動方式
            grabCursor: false,
            centeredSlides: true, //置中
            initialSlide: 2, //起始幻燈片
            spaceBetween: 20,
            // slidesPerView: '2',
            slidesPerView: 'auto',
            // init: false,
            loopedSlides: '5',
            touchRatio: 0,
            loop: true,
            navigation: {
                nextEl: '.btn_next',
                prevEl: '.btn_prev',
            },
        });


        var swiper = new Swiper('.gallery-top', {
            effect: 'coverflow', //滑動方式
            grabCursor: false,
            centeredSlides: true, //置中
            initialSlide: 2, //起始幻燈片
            // slidesPerView: '2',
            slidesPerView: 'auto',
            // init: false,
            loopedSlides: '2',
            touchRatio: 0,
            loop: true,
            coverflowEffect: {
                rotate: 20,
                stretch: 350,
                depth: 400,
                modifier: 1,
                shadow: false, // 顯示陰影
                slideShadows: false,
            },
            navigation: {
                nextEl: '.btn_next',
                prevEl: '.btn_prev',
            },
            thumbs: {
                swiper: swiper2,
            },
        });
    } else {
        // 手機版
        var swiper2 = new Swiper('.gallery-txt', {
            effect: 'slide', //滑動方式
            grabCursor: false,
            centeredSlides: true, //置中
            initialSlide: 2, //起始幻燈片
            spaceBetween: 20,
            slidesPerView: 'auto',
            loopedSlides: '5',
            touchRatio: 0,
            loop: true,
            navigation: {
                nextEl: '.btn_next',
                prevEl: '.btn_prev',
            },
        });


        var swiper = new Swiper('.gallery-top', {
            effect: 'coverflow', //滑動方式
            grabCursor: false,
            centeredSlides: true, //置中
            initialSlide: 1, //起始幻燈片
            slidesPerView: 'auto',
            loopedSlides: '2',
            touchRatio: 0,
            loop: true,
            coverflowEffect: {
                rotate: 0,
                stretch: 290,
                depth: 50,
                modifier: 1,
                shadow: false, // 顯示陰影
                slideShadows: false,
            },
            navigation: {
                nextEl: '.btn_next',
                prevEl: '.btn_prev',
            },
            thumbs: {
                swiper: swiper2,
            },
        });
    }



    if (winW > 768) {

        var i = 0;

        function swipergo() {
            var _scroll = $win.scrollTop();
            var _sec7pos = $('.sec7').offset().top;

            if (i == 0) {
                if (_scroll <= _sec7pos - 500) {
                    TweenMax.to($('.gallery-top .swiper-slide-next').next().next(), 1, { x: -1300 });
                    TweenMax.to($('.gallery-top  .swiper-slide-next').next(), 1, { x: -1000 });
                    TweenMax.to($('.gallery-top  .swiper-slide-prev').prev().prev(), 1, { x: +1300 });
                    TweenMax.to($('.gallery-top  .swiper-slide-prev').prev(), 1, { x: +1000 });
                    TweenMax.to('.gallery-top .swiper-slide-prev', 1, { x: +600 });
                    TweenMax.to('.gallery-top .swiper-slide-next', 1, { x: -600 });
                    TweenMax.to('.gallery-top .swiper-slide-duplicate', 1, { autoAlpha: 0, ease: Linear.easeNone });
                } else if (_scroll = _sec7pos - 200) {
                    TweenMax.to('.gallery-top .swiper-slide-prev', 3, { x: +350 });
                    TweenMax.to('.gallery-top .swiper-slide-prev', 3, { z: -400 });
                    TweenMax.to('.gallery-top .swiper-slide-next', 3, { x: -350 });
                    TweenMax.to('.gallery-top .swiper-slide-next', 3, { z: -400 });
                    TweenMax.to($('.gallery-top .swiper-slide-prev').prev(), 3, { x: +700 });
                    TweenMax.to($('.gallery-top .swiper-slide-prev').prev(), 3, { z: -800 });
                    TweenMax.to($('.gallery-top .swiper-slide-next').next(), 3, { x: -700 });
                    TweenMax.to($('.gallery-top .swiper-slide-next').next(), 3, { z: -800 });
                    TweenMax.to('.gallery-top .swiper-slide-duplicate', 5, { autoAlpha: 1, ease: Linear.easeNone });
                    i++;
                    console.log(i);
                }
            } else {}
        }
        $win.scroll(swipergo);
    } else {


    }














    $('.btn_next').click(function() {

        $('.mainIframe').each(function() {
            this.contentWindow.postMessage('{"event": "command", "func": "stopVideo", "args": ""}', '*');
        });

    });


    $('.btn_prev').click(function() {
        $('.mainIframe').each(function() {
            this.contentWindow.postMessage('{"event": "command", "func": "stopVideo", "args": ""}', '*');
        });

    });





    // $('.sec8 .slidebox').slick({
    //     slidesToShow: 3,
    //     slidesToScroll: 1,
    //     infinite: true,
    //     dots: true,
    //     arrows: true,
    //     autoplay: true,
    //     autoplaySpeed: 3500,
    //     speed: 300,
    //     responsive: [{
    //             breakpoint: 1024,
    //             settings: {
    //                 slidesToShow: 2
    //             }
    //         },
    //         {
    //             breakpoint: 640,
    //             settings: {
    //                 slidesToShow: 1
    //             }
    //         }
    //     ]
    // });

    $('.btnback').click(function() {
        $('html,body').animate({ scrollTop: 0 }, 333);
    });


    /* header fix ---------------------------------------------------*/

    var _sec2pos = $('.sec2').offset().top;
    var _sec7pos = $('.sec7').offset().top;

    function headerfix() {
        var _scroll = $win.scrollTop();

        if (_scroll >= _sec2pos / 3) {
            $('.myheader2').fadeIn('fast');
        } else {
            $('.myheader2').fadeOut('fast');
        }
    }

    $win.scroll(headerfix);






    if (winW > 768) {

        function red() {

            var _scroll = $win.scrollTop();

            if (_scroll > _sec2pos) {

                if (_scroll <= _sec7pos - 200) {
                    $('.navbtn.red').addClass('on');

                } else {
                    $('.navbtn.red').removeClass('on');
                }

            } else {
                $('.navbtn.red').removeClass('on');
            }
        }

        $win.scroll(red);

    } else {

    }







    /* 選單 ---------------------------------------------------*/

    // 手機fix
    // function navfix() {
    //     var _scroll = $win.scrollTop();
    //     if( winW<=768 ){
    //         if(_scroll >= 65){
    //             $('.nav').addClass('fixed');
    //         }else{
    //             $('.nav').removeClass('fixed');
    //         }
    //     }else{
    //         $('.nav').removeClass('fixed');
    //     }
    // }
    // navfix();
    // $win.scroll(navfix);

    // // 展開收闔
    // function navslide() {
    //     var _pos = $('.sec1').offset().top;
    //     var _scroll = $win.scrollTop();
    //     if( _scroll >= _pos /2){
    //         $('.nav').removeClass('open');
    //     }
    // }
    // $win.scroll(navslide);


    // 重設
    function navde() {

        $('.mainnav').find('li').removeClass('open');

        // $('.subnav').hide();

        $('.burger').attr('toggle', '0');
        $('.burger').removeClass('isclick');

        $('.mainnav').css('height', '');
        $('.myheader').css('height', 'auto');

        TweenMax.to('.myheader', .1, { backgroundColor: 'rgba(255, 255, 255, 0.95)', ease: Quart.easeOut });

        // TweenMax.to($('.myheader').find('.logo').find('a'), .1, { autoAlpha: 1, ease: Linear.easeNone });


        // $('.logonav').hide();

        $('body').css({
            'overflow-y': '',
            'position': '',
            'height': ''
        });

        // $('.mainnav').css('display','flex');

        if (winW <= 768) {
            $('.mainnav').hide();
        } else {
            $('.mainnav').show();
        }
    }
    navde();

    // 主選單
    function navfunc() {
        var navopen = $('.burger').attr('toggle');
        if (navopen == 0) {

            $('.myheader').css({
                'height': '100vh',
                'overflow': 'auto'
            });
            // $('.mainnav ul').css('overflow-y','scroll');

            TweenMax.to('.myheader', .1, { backgroundColor: '#fff', ease: Quart.easeOut });

            $('.mainnav').slideDown();

            // TweenMax.to($('.myheader').find('.logo').find('a'), .1, { autoAlpha: 0, ease: Linear.easeNone });
            // $('.logonav').show();


            $('body').css({
                'overflow-y': 'hidden',
                'position': 'fixed',
                'height': '100%'
            });

            $('.burger').attr('toggle', '1');
            $('.burger').addClass('isclick');

        } else if (navopen == 1) {

            $('.mainnav').css('height', '');
            $('.myheader').css('height', '');
            TweenMax.to('.myheader', .5, { backgroundColor: 'rgba(255, 255, 255, 0.95)', delay: .3, ease: Quart.easeOut });
            $('.mainnav').slideUp();

            // TweenMax.to($('.myheader').find('.logo').find('a'), .1, { autoAlpha: 1, ease: Linear.easeNone });
            // $('.logonav').hide();

            $('body').css({
                'overflow-y': '',
                'position': '',
                'height': ''
            });

            $('.burger').attr('toggle', '0');
            $('.burger').removeClass('isclick');
        }
    }
    $('.burger').click(navfunc);


    // 子選單
    // function subnavfunc() {
    //     if( $('.subnav').length ){

    //         $(this).toggleClass('open');
    //         if( $(this).hasClass('open') ){
    //             $(this).find('.subnav').slideDown();
    //         }else{
    //             $(this).find('.subnav').slideUp();
    //         }

    //     }
    // }
    // $('.mainnav').find('li').click(subnavfunc);



    // 影片區

    // $('.playlist .switchVideo').on('click', playThis);

    // function playThis() {
    //     var vid = $(this).data('vid');
    //     var pdname = $(this).data('pdname');
    //     var slogan = $(this).data('slogan');
    //     $('.playlist .switchVideo').removeClass('active');
    //     $(this).addClass('active');
    //     $('#mainIframe').attr("src", "https://www.youtube.com/embed/" + vid + "?rel=0&autoplay=1");
    //     $('.mainVideo > .pdName').text(pdname);
    //     $('.mainVideo > .pdSlogan').text("[" + slogan + "]");
    // }


   /* nav scroll --------------------------------------------*/

    /* page scroll */
    var thispage = 0;
    var pagescroll = true;
    var pageT = [],
        $sec = $('.sec'),
        $main = $('.wrapper'),
        ah = $main.outerHeight(true);
    // var _thispagescroll = pageName.includes(hash);



    // var hash = location.hash;

    var $navbtn = $('.navbtn');

    // var navRe = function() {
    //     $('.btn_nav').removeClass('isclick');
    //     $('.nav').fadeOut();
    //     $('.nav .inner').removeClass('in');
    // }

    var pageReset = function() {
        pageT = [];
        winW = $win.outerWidth();
        ah = $main.outerHeight(true);
        $sec.each(
            function(i, obj) {
                var t = $(obj).offset().top;
                pageT.push(t);
                if (i == $sec.length - 1) { pageT.push(ah); }
            }
        );

        // console.log(pageT)
    }

    var menuselect = function() {
        $navbtn.removeClass('select');

        // console.log(thispage)

        pageReset();
        // if (winW>=1024){
        //     $('.navbtn[data-page="' + thispage + '"]').addClass('select');
        // }
        $('.navbtn[data-page="' + thispage + '"]').addClass('select');


    }

     var menuscroll = function(n) {
        var _pos;
        thispage = n;
        // if(thispage!==1){sliderclose();}
        pageReset();

        // if (winW <= _media) { $headerH = 47; } else { $headerH = 50; }
        // if (n !== 0) { _pos = $headerH; } else { _pos = 0; }

        // TweenMax.to('body,html', .8, { scrollTop: pageT[thispage] - headerH , ease: Quart.easeOut });


        // $body.animate({
        //     scrollTop: pageT[thispage] - headerH
        // }, 600);

          $body.animate({
            scrollTop: pageT[thispage] - headerH - 50
        }, 600);


        // if(thispage == 4){ sec5Pagechange();}
        // _menuopen = true;
        // menuClick();
    }

    var scrollPage = function(wt, whelf) {
        winH = $win.height();
        winW = $win.outerWidth();
        wt = $win.scrollTop();
        whelf = winH / 3;
        // _mainTOP = $main.offset().top;

        for (var p = 0; p < $sec.length; p++) {
            if (wt > pageT[p] - whelf && wt < pageT[p + 1] - whelf) {
                thispage = p;
            }
        }

        // if(winW > _media){
        //     if(thispage == 1 && autoOpen){
        //         slideropen();
        //         autoOpen=false;
        //     }
        //     else if(thispage !==1){
        //         autoOpen=true;
        //     }
        // }else{
        //     if(thispage == 1 && autoOpen){slideropen(); console.log(autoOpen); autoOpen=false;}
        // }

        // if (wt > _mainTOP) {
        //     var w = $header.width();
        //     $header.addClass('fix');
        //     $header.css('marginLeft',-(w/2));
        // } else {
        //     $header.removeClass('fix');
        //     $header.css('marginLeft','0');
        // }

        menuselect();

        href = pageName[thispage];
        window.location.hash = href;
    }

    var scrollthispage = function() {
        var _pos;
        _thispagescroll = pageName.indexOf(hash.substr(1));

        // console.log(_thispagescroll)

        pageReset();

        var topH = $('.myheader2').height();

        if (_thispagescroll != -1) {
            thispage = pageName.indexOf(hash.substr(1));

            pageReset();

            // if (winW <= _media) { $headerH = 47; } else { $headerH = 50; }
            // if (thispage !== 0) { _pos = $headerH; } else { _pos = 0; }
            if (thispage == 0) {
                TweenMax.to('body,html', .8, { scrollTop: 0, ease: Quart.easeOut });
            } else {
                TweenMax.to('body,html', .8, { scrollTop: pageT[thispage] - topH, ease: Quart.easeOut });
                // $body.animate({
                //     scrollTop: pageT[thispage] 
                // }, 600);
            }

            menuselect();
            href = pageName[thispage];
            window.location.hash = href;
            pagescroll = false;
        } else {

            TweenMax.to('body,html', .8, { scrollTop: 0, ease: Quart.easeOut });

            // lbhref = window.location.hash.substr(1);
            // TweenMax.to('body,html', .8, { scrollTop: 0, ease: Quart.easeOut,onComplete:function()


        };

    }
    setTimeout(function() {
        scrollthispage()
    }, 600);
    
    $navbtn.click(
        function() {
            var n = $(this).data('page');
            // if($(this).attr('data-sec5list')) thisS5 = $(this).data('sec5list');
            menuscroll(n);

            navde();
            // if(winW<=1024){ navRe();}

            // if(_lb)lbClose();
            // _lb = true;
        }
    );




    // /* sec4 切換 -----------------------------------------------------*/

    // var thisS4 = 0,
    //     prevS4 = '',
    //     $btn_sec4 = $('.sec4 .sidebar li');

    // var sec4Pagechange = function() {
    //     if (thisS4 !== prevS4) $('.sec4 .inner').fadeOut(0);
    //     $('.sec4 .inner[data-inner="' + thisS4 + '"]').fadeIn(500);
    //     $btn_sec4.removeClass('select');
    //     $btn_sec4.eq(thisS4).addClass('select');
    //     prevS4 = thisS4;
    // }
    // sec4Pagechange(thisS4);
    // $btn_sec4.click(function() {
    //     thisS4 = $(this).index();
    //     sec4Pagechange();
    // });




    // /* sec5 切換 -----------------------------------------------------*/

    // var thisS5 = 0,
    //     prevS5 = '',
    //     $btn_sec5 = $('.sec5 .tabbtn');

    // var sec5Pagechange = function() {
    //     if (thisS5 !== prevS5) $('.sec5 .list').fadeOut(0);
    //     $('.sec5 .list[data-list ="' + thisS5 + '"]').fadeIn(500);
    //     $btn_sec5.removeClass('select');
    //     $btn_sec5.eq(thisS5).addClass('select');
    //     prevS5 = thisS5;
    // }
    // sec5Pagechange(thisS5);
    // $btn_sec5.click(function() {
    //     thisS5 = $(this).index();
    //     sec5Pagechange();
    // });







    /* lightbox
    ------------------------------------------------------------*/

    // var lightbox = function(){

    // var $lb = $('.lb'),
    //     $lbbox = $('.lbbox');
    // var _vdUrl = 'https://www.youtube.com/embed/'

    // var lb_data;
    // // lbname = 'announcement';

    // // TweenMax.set($('.lb'),{autoAlpha:0});
    // // TweenMax.set($('.lb') ,{css:{zIndex:-1}});

    // var lbOpen = function(lb_data) {

    //     var lbAni = new TimelineMax();
    //     lbAni.to($('.lb[data-lb="' + lb_data + '"]'), .5, { css: { zIndex: 1000 } }, '-=.5')
    //         .to($('.lb[data-lb="' + lb_data + '"]'), .5, { autoAlpha: 1, ease: Linear.easeNone });
    // }

    // var lbClose = function() {

    //     var lbCloseAni = new TimelineMax();
    //     lbCloseAni.to($('.lb'), .5, { autoAlpha: 0, ease: Linear.easeNone })
    //         .to($('.lb'), .5, { css: { zIndex: -1 } });

    //     $('#youtuUrl').attr('src', '');
    // }

    // // lbOpen(lbname);
    // $('.libtn').click(function() {
    //     var lbname = $(this).data('lb');
    //     var ytid = $(this).attr('ytid');

    //     lbOpen(lbname);
    //     $('#youtuUrl').attr('src', _vdUrl + ytid + '?autoplay=1&wmode=opaque&showinfo=0');

    //     return false;
    // });


    // $('.lb .btn_x').click(lbClose);


    // }
    // lightbox();

    $('body').click(function(e) {
        var _con = $('.lbbox'); // 设置目标区域
        if (!_con.is(e.target) && _con.has(e.target).length === 0) { // Mark 1
            lbClose();
        }
    });













    /* resize
    ------------------------------------------------------------*/

    var _resize = function() {

        winW = $win.outerWidth();
        winH = $win.height();
        docH = $(document).height();
        _scroll = $(window).scrollTop();
        scrollBottom = $(window).scrollTop() + $(window).height();
        headerH = $('.myheader2').height();


        // _sec1pos = $('.sec1').offset().top;

        navde();
        // if (pagescroll || winW > 1000) scrollthispage();

        // navfix();

    }
    $win.scroll(scrollPage);
    _resize();
    $win.resize(function() {

        // _resize();

    });





    var windowHeight = $(window).height();

    // Resize Event
    $(window).resize(function() {

        // Check window width has actually changed and it's not just iOS triggering a resize event on scroll
        if ($(window).width() != windowHeight) {

            // Update the window width for next time
            windowHeight = $(window).width();

            // Do stuff here
            _resize();
            // if (pagescroll || windowHeight > 768) scrollthispage();

        }

    });



}





$(function() {
    // $('header').load('include/header.html');
    // $('footer').load('include/footer.html');

    // var imagesLoaded = loading();
    //        imagesLoaded.loadfunc();
    //        imagesLoaded.callback = function(){

    //          script();

    //        };

    script();

    $('.gb-footer-2019__btn-top').click(function() {
        var $body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');
        $body.animate({
            scrollTop: 0
        }, 600);
    })



});