$(document).ready(function () {

                /* sidebar - start */

                // sidebar settings
                $('.ui.sidebar').sidebar({
                    dimPage: true,
                    transition: 'overlay',
                    mobileTransition: 'overlay'
                });

                // create sidebar and attach to menu open
                $('.ui.sidebar.left')
                    .sidebar('attach events', '#left-sidebar-button')
                    .sidebar('attach events', '#right-sidebar-button', 'hide');

                $('.ui.sidebar.right')
                    .sidebar('attach events', '#right-sidebar-button')
                    .sidebar('attach events', '#left-sidebar-button', 'hide');

                $('.ui.sidebar.menu a.item')
                    .on('click', function () {
                        $(this)
                            .addClass('active')
                            .siblings()
                            .removeClass('active');
                    });

                /* sidebar - end */

          

            });