/** 
 * PrimeFaces Atlantis Layout
 */
PrimeFaces.widget.Atlantis = PrimeFaces.widget.BaseWidget.extend({
    
    init: function(cfg) {
        this._super(cfg);
        this.wrapper = $(document.body).children('.layout-wrapper');
        this.sidebar = this.wrapper.children('.layout-sidebar');
        this.tabMenu = this.jq;
        this.tabMenuNav = this.tabMenu.children('.layout-tabmenu-nav');
        this.tabMenuNavLinks = this.tabMenuNav.find('a');
        this.tabMenuContents = this.tabMenu.find('.layout-tabmenu-content');
        this.topbar = this.wrapper.children('.topbar');
        this.topbarMenu = this.topbar.children('.topbar-menu');
        this.topbarItems = this.topbarMenu.children('li');
        this.topbarLinks = this.topbarItems.children('a');
        this.topbarMenuButton = $('#topbar-menu-button');        
        this.menuActive = false;
        this.sidebarClick = false;
        this.topbarLinkClick = false;
        this.topbarMenuClick = false;

        this._bindEvents();
        this._restoreMenuState();
    },
    
    _bindEvents: function() {
        var $this = this;
        
        this.tabMenu.find('.menu-button').on('click', function(e) {
            $this.sidebar.css('overflow','visible');
            $this.wrapper.removeClass('layout-wrapper-menu-active');
            if(!$this.isOverlayMenu()) {
                $(window).trigger('resize');
            }
            
            e.preventDefault();
        });
        
        this.tabMenu.find('.menu-pin-button').on('click', function(e) {
            var icon = $(this).children('i');
            if(icon.hasClass('fa-rotate-90'))
                $this._saveMenuState($(this).closest('.layout-tabmenu-content').index());
            else
                $this.clearMenuState();
                
            $this.tabMenuContents.find('.menu-pin-button').children('i').toggleClass('fa-rotate-90');
            e.preventDefault();
        });
        
        this.tabMenuNavLinks.on('click', function(e) {
            $this.sidebar.css('overflow','hidden');
            var link = $(this);
            link.parent().addClass('active-item').siblings('.active-item').removeClass('active-item');
            $this.wrapper.addClass('layout-wrapper-menu-active');
            $this.tabMenu.find('.layout-tabmenu-content').removeClass('layout-tabmenu-content-active').
                    eq(link.parent().index()).addClass('layout-tabmenu-content-active');
            
            if(!$this.isOverlayMenu()) {
                $(window).trigger('resize');
            }
            $(this).children('.layout-tabmenu-tooltip').hide();
            e.preventDefault();
        });
        
        if(!this.isIOS()) {
            this.tabMenuNavLinks.on('mouseenter', function(e) {
                var link = $(this);
                if(!link.parent().hasClass('active-item')||!$this.wrapper.hasClass('layout-wrapper-menu-active')) {
                    link.children('.layout-tabmenu-tooltip').show();
                }
            })
            .on('mouseleave', function(e) {
                $(this).children('.layout-tabmenu-tooltip').hide();
            });
        }

        this.topbarMenuButton.on('click', function(e) {
            $this.topbarMenuClick = true;
            $this.topbarMenu.find('ul').removeClass('fadeInDown fadeOutUp');

            if($this.topbarMenu.hasClass('topbar-menu-visible')) {
                $this.topbarMenu.addClass('fadeOutUp');
                
                setTimeout(function() {
                    $this.topbarMenu.removeClass('fadeOutUp topbar-menu-visible');
                },500);
            }
            else {
                $this.topbarMenu.addClass('topbar-menu-visible fadeInDown');
            }
                        
            e.preventDefault();
        });
                
        this.topbarLinks.on('click', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = link.next();
            
            $this.topbarLinkClick = true;

            item.siblings('.active-topmenuitem').removeClass('active-topmenuitem');

            if($this.isDesktop()) {
                if(submenu.length) {
                    if(item.hasClass('active-topmenuitem')) {
                        submenu.addClass('fadeOutUp');
                        
                        setTimeout(function() {
                            item.removeClass('active-topmenuitem'),
                            submenu.removeClass('fadeOutUp');
                        },500);
                    }
                    else {
                        item.addClass('active-topmenuitem');
                        submenu.addClass('fadeInDown');
                    }
                }
            }
            else {
                item.children('ul').removeClass('fadeInDown fadeOutUp');
                item.toggleClass('active-topmenuitem');
            }   
                        
            e.preventDefault();   
        });
        
        this.topbarMenu.children('.search-item').on('click', function(e) {
            $this.topbarLinkClick = true;
        });
        
        this.sidebar.off('click.sidebar').on('click.sidebar', function(e) {
            $this.sidebarClick = true;
        });
        
        $(document.body).on('click', function() {
            if(!$this.topbarMenuClick && !$this.topbarLinkClick) {
                $this.topbarItems.filter('.active-topmenuitem').removeClass('active-topmenuitem');
                $this.topbarMenu.removeClass('topbar-menu-visible');
            }
            
            if(!$this.sidebarClick && ($this.isOverlayMenu() || !$this.isDesktop())) {
                $this.wrapper.removeClass('layout-wrapper-menu-active');
            }
            
            $this.topbarLinkClick = false;
            $this.topbarMenuClick = false;
            $this.sidebarClick = false;
        });
    },
    
    isTablet: function() {
        var width = window.innerWidth;
        return width <= 1024 && width > 640;
    },

    isDesktop: function() {
        return window.innerWidth > 1024;
    },

    isMobile: function() {
        return window.innerWidth <= 640;
    },
    
    isOverlayMenu: function() {
        return this.wrapper.hasClass('layout-overlay-menu');
    },
    
    isIOS: function(e) {
        return ((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPod/i)) || (navigator.userAgent.match(/iPad/i)));
    },
    
    _saveMenuState: function(index) {
        $.cookie('atlantis_tabmenu_index', index.toString(), {path: '/'});
    },
    
    _restoreMenuState: function() {
        var activeTabMenu = $.cookie('atlantis_tabmenu_index');
        if(activeTabMenu) {
            var tabContents = this.tabMenu.find('.layout-tabmenu-content');
            tabContents.find('.menu-pin-button').children('i').removeClass('fa-rotate-90');
            var index = parseInt(activeTabMenu);
            this.wrapper.addClass('layout-wrapper-menu-active');
            this.tabMenuNavLinks.eq(index).parent('li').addClass('active-item');
            tabContents.eq(index).addClass('layout-tabmenu-content-active');
        }
    },
    
    clearMenuState: function() {
        $.removeCookie('atlantis_tabmenu_index', {path: '/'});
    }
    
});

/** 
 * PrimeFaces AtlantisMenu Component
 */
PrimeFaces.widget.AtlantisMenu = PrimeFaces.widget.BaseWidget.extend({
    
    init: function(cfg) {
        this._super(cfg);
        this.menulinks = this.jq.find('a');
        this.expandedMenuitems = this.expandedMenuitems || [];     
        this.menuActive = false;
        this.topbarLinkClick = false;
        this.topbarMenuClick = false;
        this.nano = this.jq.closest('.nano');

        this._bindEvents();
        this.restoreMenuState();
    },
    
    _bindEvents: function() {
        var $this = this;
        
        this.menulinks.off('click').on('click', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = item.children('ul');
                                     
            if(item.hasClass('active-menuitem')) {
                if(submenu.length) {
                    $this.removeMenuitem(item.attr('id'));
                    item.removeClass('active-menuitem');
                    submenu.slideUp();
                }
            }
            else {
                $this.addMenuitem(item.attr('id'));
                $this.deactivateItems(item.siblings(), true);
                $this.activate(item);
            }
            
            setTimeout(function() {
                $this.nano.nanoScroller();
            }, 500);
                                    
            if(submenu.length) {
                e.preventDefault();
            }
        });
    },
         
    activate: function(item) {
        var submenu = item.children('ul');
        item.addClass('active-menuitem');

        if(submenu.length) {
            submenu.slideDown();
        }
    },
    
    deactivate: function(item) {
        var submenu = item.children('ul');
        item.removeClass('active-menuitem');
        
        if(submenu.length) {
            submenu.hide();
        }
    },
        
    deactivateItems: function(items, animate) {
        var $this = this;
        
        for(var i = 0; i < items.length; i++) {
            var item = items.eq(i),
            submenu = item.children('ul');
            
            if(submenu.length) {
                if(item.hasClass('active-menuitem')) {
                    var activeSubItems = item.find('.active-menuitem');
                    item.removeClass('active-menuitem');
                    item.find('.ink').remove();
                    
                    if(animate) {
                        submenu.slideUp('normal', function() {
                            $(this).parent().find('.active-menuitem').each(function() {
                                $this.deactivate($(this));
                            });
                        });
                    }
                    else {
                        submenu.hide();
                        item.find('.active-menuitem').each(function() {
                            $this.deactivate($(this));
                        });
                    }
                    
                    $this.removeMenuitem(item.attr('id'));
                    activeSubItems.each(function() {
                        $this.removeMenuitem($(this).attr('id'));
                    });
                }
                else {
                    item.find('.active-menuitem').each(function() {
                        var subItem = $(this);
                        $this.deactivate(subItem);
                        $this.removeMenuitem(subItem.attr('id'));
                    });
                }
            }
            else if(item.hasClass('active-menuitem')) {
                $this.deactivate(item);
                $this.removeMenuitem(item.attr('id'));
            }
        }
    },
    
    clearActiveItems: function() {
        var activeItems = this.jq.find('li.active-menuitem'),
        subContainers = activeItems.children('ul');

        activeItems.removeClass('active-menuitem');
        subContainers.hide();
    },
            
    removeMenuitem: function (id) {
        this.expandedMenuitems = $.grep(this.expandedMenuitems, function (value) {
            return value !== id;
        });
        this.saveMenuState();
    },
    
    addMenuitem: function (id) {
        if ($.inArray(id, this.expandedMenuitems) === -1) {
            this.expandedMenuitems.push(id);
        }
        this.saveMenuState();
    },
    
    saveMenuState: function() {
        $.cookie('atlantis_expandeditems', this.expandedMenuitems.join(','), {path: '/'});
    },
    
    clearMenuState: function() {
        $.removeCookie('atlantis_expandeditems', {path: '/'});
    },
        
    restoreMenuState: function() {
        var menucookie = $.cookie('atlantis_expandeditems');
        if (menucookie) {
            this.expandedMenuitems = menucookie.split(',');
            for (var i = 0; i < this.expandedMenuitems.length; i++) {
                var id = this.expandedMenuitems[i];
                if (id) {
                    var menuitem = $("#" + this.expandedMenuitems[i].replace(/:/g, "\\:"));
                    menuitem.addClass('active-menuitem');
                    
                    var submenu = menuitem.children('ul');
                    if(submenu.length) {
                        submenu.show();
                    }
                }
            }
        }
        
        var inlineProfileCookie = $.cookie('atlantis_inlineprofile_expanded');
        if (inlineProfileCookie) {
            this.profileMenu.show().prev('.profile').addClass('profile-expanded');
        }
    }
    
});

$(document).ready(function() {
    $('.nano').nanoScroller({flash:true});
});

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD (Register as an anonymous module)
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// Node/CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {},
			// To prevent the for loop in the first place assign an empty array
			// in case there are no cookies at all. Also prevents odd result when
			// calling $.cookie().
			cookies = document.cookie ? document.cookie.split('; ') : [],
			i = 0,
			l = cookies.length;

		for (; i < l; i++) {
			var parts = cookies[i].split('='),
				name = decode(parts.shift()),
				cookie = parts.join('=');

			if (key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));

/* Issue #924 is fixed for 5.3+ and 6.0. (compatibility with 5.3) */
if(window['PrimeFaces'] && window['PrimeFaces'].widget.Dialog) {
    PrimeFaces.widget.Dialog = PrimeFaces.widget.Dialog.extend({
        
        enableModality: function() {
            this._super();
            $(document.body).children(this.jqId + '_modal').addClass('ui-dialog-mask');
        },
        
        syncWindowResize: function() {}
    });
}