/**
 * Created by PYRO on 30.06.16.
 */
'use strict';

/**
 * @module
 * @name LoginModule
 */

var LoginModule = angular.module('Login', ['ngMaterial'])
.config(function($mdThemingProvider) {
		$mdThemingProvider.definePalette('custom', {
			'50': '2f2e2e',
			'100': '2f2e2e',
			'200': '2f2e2e',
			'300': '2f2e2e',
			'400': '2f2e2e',
			'500': '2f2e2e',
			'600': '2f2e2e',
			'700': '2f2e2e',
			'800': '2f2e2e',
			'900': '2f2e2e',
			'A100': '44dbbe',
			'A200': '44dbbe',
			'A400': '44dbbe',
			'A700': '44dbbe',
			'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
			// on this palette should be dark or light

			'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
			'200', '300', '400', 'A100'],
			'contrastLightColors': undefined    // could also specify this if default was 'dark'
		});
		$mdThemingProvider.theme("default").primaryPalette("custom")
						   .accentPalette("custom")
						   .warnPalette("custom");
});
