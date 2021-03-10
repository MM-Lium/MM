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
        pageName = ['kv', 'z-series', 'n20', 's20fe-a-series', 'tab-a7-s7', 'wa', 'video', 'moresp', 'buy'];

    var _thispagescroll = pageName.indexOf(hash);


    AOS.init({
        offset: 150,
        duration: 800,
        delay: 0,
        easing: 'ease-in-out-sine',
        anchorPlacement: 'top-bottom',
    });


    /* slick ---------------------------------------------------*/



    if (winW > 768) {
        var swiper2 = new Swiper('.gallery-txt', {
            effect: 'slide', //滑動方式
            grabCursor: false,
            centeredSlides: true, //置中
            initialSlide: 2, //起始幻燈片
            spaceBetween: 50,
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
            effect: 'slide', //滑動方式
            grabCursor: false,
            centeredSlides: true, //置中
            initialSlide: 2, //起始幻燈片
            spaceBetween: -500,
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

    } else {
        // 手機版
        var swiper2 = new Swiper('.gallery-txt', {
            effect: 'slide', //滑動方式
            grabCursor: false,
            centeredSlides: true, //置中
            initialSlide: 2, //起始幻燈片
            spaceBetween: 100,
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
            effect: 'slide', //滑動方式
            grabCursor: false,
            centeredSlides: true, //置中
            initialSlide: 2, //起始幻燈片
            slidesPerView: 'auto',
            loopedSlides: '5',
            touchRatio: 0,
            loop: true,
            navigation: {
                nextEl: '.btn_next',
                prevEl: '.btn_prev',
            },
            // thumbs: {
            //     swiper: swiper2,
            // },
        });
    }


    $('.sec1 .allkv').slick({
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        dots: true,
        arrows: false
    });


    if (winW > 768) {

        var i = 0;

        function swipergo() {
            var _scroll = $win.scrollTop();
            var _sec7pos = $('.sec7').offset().top;

            if (i == 0) {
                if (_scroll <= _sec7pos - 500) {

                    $('.gallery-top  .swiper-slide-next').next().css('transform', 'translate( -30% ,0)');
                    $('.gallery-top  .swiper-slide-prev').prev().css('transform', 'translate( 30% ,0)');

                    $('.gallery-top .swiper-slide-prev').css('transform', 'translate( 15% ,0)');
                    $('.gallery-top .swiper-slide-next').css('transform', 'translate( -15% ,0)');

                    // TweenMax.to('.gallery-top .swiper-slide', 1, { autoAlpha: 0, ease: Linear.easeNone });
                    TweenMax.to('.gallery-top .swiper-slide-duplicate', 1, { autoAlpha: 0, ease: Linear.easeNone });
                    TweenMax.to('.gallery-top .swiper-slide-active', 1, { autoAlpha: 1, ease: Linear.easeNone });


                } else if (_scroll = _sec7pos) {

                    $('.gallery-top .swiper-slide-prev').css('transform', 'translate( 0 ,0)');
                    $('.gallery-top .swiper-slide-next').css('transform', 'translate( 0 ,0)');

                    $('.gallery-top  .swiper-slide-next').next().css('transform', 'translate( 0 ,0)');
                    $('.gallery-top  .swiper-slide-prev').prev().css('transform', 'translate( 0 ,0)');

                    // TweenMax.to('.gallery-top .swiper-slide', .5, { autoAlpha: 1, ease: Linear.easeNone });
                    TweenMax.to('.gallery-top .swiper-slide-duplicate', 1, { autoAlpha: 1, delay: 2, ease: Linear.easeNone });
                    i++;
                    console.log(i);
                }
            } else {}
        }
        $win.scroll(swipergo);
    } else {

    }


    var go = 0;

    function swipergo2() {
        var _scroll = $win.scrollTop();
        var _sec8pos = $('.sec8').offset().top;

        if (go == 0) {
            if (_scroll >= _sec8pos - 500) {
                $('.sec8 .title').addClass('go');
                $('.sec8 .title').css("display", "block");

                go++;
            }

        } else {}
    }

    $win.scroll(swipergo2);




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


    $('.btnback').click(function() {
        $('html,body').animate({ scrollTop: 0 }, 333);
    });


    /* header fix ---------------------------------------------------*/

    var _sec2pos = $('.sec2').offset().top;
    var _sec5pos = $('.sec5').offset().top;
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

    var f = 0;


    if (winW > 768) {


        function flower() {

            var _scroll = $win.scrollTop();

            if (_scroll >= _sec5pos - 200) {
                $('.flower').addClass('go');

            } else if (_scroll <= _sec5pos - 500) {
                $('.flower').removeClass('go');
            }

        }

        $win.scroll(flower);

    } else {

        function flower() {

            var _scroll = $win.scrollTop();

            if (_scroll >= _sec5pos - 250) {
                $('.flower').addClass('go');

            } else if (_scroll <= _sec5pos - 500) {
                $('.flower').removeClass('go');
            }
        }

        $win.scroll(flower);

    }


    if (winW > 768) {

        function red() {

            var _scroll = $win.scrollTop();

            if (_scroll > _sec2pos) {

                if (_scroll < _sec7pos) {

                    if ($('.mainnav li').hasClass('select')) {

                        $('.navbtn.red').removeClass('on');

                    } else {
                        $('.navbtn.red').addClass('on');
                    }

                } else {
                    
                    $('.navbtn.red').removeClass('on');
                }

            } else {
                $('.navbtn.red').removeClass('on');
            }
        }

    } else {

    }

    $win.scroll(red);


    // 重設
    function navde() {

        $('.mainnav').find('li').removeClass('open');

        // $('.subnav').hide();

        $('.burger').attr('toggle', '0');
        $('.burger').removeClass('isclick');

        $('.mainnav').css('height', '');
        $('.myheader').css('height', '');

        TweenMax.to('.myheader', .1, { backgroundColor: 'rgba(255, 255, 255, 1)', ease: Quart.easeOut });

        $('body').css({
            'overflow-y': '',
            'position': '',
            'height': ''
        });


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
                'overflow': ''
            });

            TweenMax.to('.myheader', .1, { backgroundColor: '#fff', ease: Quart.easeOut });

            $('.mainnav').slideDown();

            $('body').css({
                'overflow-y': 'hidden',
                'position': 'fixed',
                'height': '100%'
            });

            $('.burger').attr('toggle', '1');
            $('.burger').addClass('isclick');

        } else if (navopen == 1) {

            $('.mainnav').css('height', '');
            $('.myheader').css('height', '17vw');
            TweenMax.to('.myheader', .5, { backgroundColor: 'rgba(255, 255, 255, 1)', delay: .3, ease: Quart.easeOut });
            $('.mainnav').slideUp();

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


    /* nav scroll --------------------------------------------*/

    /* page scroll */
    var thispage = 0;
    var pagescroll = true;
    var pageT = [],
        $sec = $('.sec'),
        $main = $('.wrapper'),
        ah = $main.outerHeight(true);


    var $navbtn = $('.navbtn');

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

    }

    var menuselect = function() {
        $navbtn.removeClass('select');


        pageReset();

        $('.navbtn[data-page="' + thispage + '"]').addClass('select');


    }

    var menuscroll = function(n) {
        var _pos;
        thispage = n;
        pageReset();

        $body.animate({
            scrollTop: pageT[thispage] - headerH - 50
        }, 600);

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

        menuselect();

        href = pageName[thispage];
        window.location.hash = href;
    }

    var scrollthispage = function() {
        var _pos;
        _thispagescroll = pageName.indexOf(hash.substr(1));

        pageReset();

        var topH = $('.myheader2').height();

        if (_thispagescroll != -1) {
            thispage = pageName.indexOf(hash.substr(1));

            pageReset();

            if (thispage == 0) {
                TweenMax.to('body,html', .8, { scrollTop: 0, ease: Quart.easeOut });
            } else {
                TweenMax.to('body,html', .8, { scrollTop: pageT[thispage] - topH, ease: Quart.easeOut });

            }

            menuselect();
            href = pageName[thispage];
            window.location.hash = href;
            pagescroll = false;
        } else {

            TweenMax.to('body,html', .8, { scrollTop: 0, ease: Quart.easeOut });
        };

    }
    setTimeout(function() {
        scrollthispage()
    }, 600);

    $navbtn.click(
        function() {
            var n = $(this).data('page');

            menuscroll(n);

            navde();

        }
    );

    // $('body').click(function(e) {
    //     var _con = $('.lbbox'); // 设置目标区域
    //     if (!_con.is(e.target) && _con.has(e.target).length === 0) { // Mark 1
    //         lbClose();
    //     }
    // });


    /* resize
    ------------------------------------------------------------*/

    var _resize = function() {

        winW = $win.outerWidth();
        winH = $win.height();
        docH = $(document).height();
        _scroll = $(window).scrollTop();
        scrollBottom = $(window).scrollTop() + $(window).height();
        headerH = $('.myheader2').height();

        navde();

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

    script();

    $('.gb-footer-2019__btn-top').click(function() {
        var $body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');
        $body.animate({
            scrollTop: 0
        }, 600);
    })

});