(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        define(['exports', 'echarts'], factory);
    } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
        factory(exports, require('echarts'));
    } else {
        factory({}, root.echarts);
    }
}(this, function (exports, echarts) {
    var log = function (msg) {
        if (typeof console !== 'undefined') {
            console && console.error && console.error(msg);
        }
    };
    if (!echarts) {
        log('ECharts is not Loaded');
        return;
    }

    var colorPalette = [
        '#a90000', '#0193a4', '#FCCE10', '#E87C25', '#B5C334',
        '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
        '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
    ];

    var theme = {
        color: colorPalette,
        textStyle: {
            fontWeight: 'normal',
            color: '#adb4c4'
        },
        title: {
            textStyle: {
                fontWeight: 'normal',
                color: '#adb4c4'
            }
        },
        visualMap: {
            color: ['#adb4c4', '#adb4c4']
        },
        toolbox: {
            iconStyle: {
                normal: {
                    borderColor: colorPalette[0]
                }
            }
        },
        tooltip: {
            backgroundColor: 'rgba(50,50,50,0.5)',
            axisPointer: {
                type: 'line',
                lineStyle: {
                    color: '#adb4c4',
                    type: 'dashed'
                },
                crossStyle: {
                    color: '#adb4c4'
                },
                shadowStyle: {
                    color: 'rgba(200,200,200,0.3)'
                }
            }
        },
        dataZoom: {
            dataBackgroundColor: 'rgba(181,195,52,0.3)',
            fillerColor: 'rgba(181,195,52,0.2)',
            handleColor: '#adb4c4'
        },
        categoryAxis: {
            axisLine: {
                lineStyle: {
                    color: '#adb4c4'
                }
            },
            splitLine: {
                show: false
            }
        },
        valueAxis: {
            axisLine: {
                show: false
            },
            splitArea: {
                show: false
            },
            splitLine: {
                lineStyle: {
                    color: ['#484c56'],
                    type: 'dashed'
                }
            }
        },
        timeline: {
            lineStyle: {
                color: '#adb4c4'
            },
            controlStyle: {
                normal: {
                    color: '#adb4c4',
                    borderColor: '#adb4c4'
                }
            },
            symbol: 'emptyCircle',
            symbolSize: 3
        },

        line: {
            itemStyle: {
                normal: {
                    borderWidth: 2,
                    borderColor: '#adb4c4',
                    lineStyle: {
                        width: 3
                    }
                },
                emphasis: {
                    borderWidth: 0
                }
            },
            symbol: 'circle',
            symbolSize: 3.5
        },
        candlestick: {
            itemStyle: {
                normal: {
                    color: '#adb4c4',
                    color0: '#adb4c4',
                    lineStyle: {
                        width: 1,
                        color: '#adb4c4',
                        color0: '#adb4c4'
                    }
                }
            }
        },
        graph: {
            color: colorPalette
        },
        legend: {
            textStyle: { "color": "#adb4c4" }
        },
        map: {
            label: {
                normal: {
                    textStyle: {
                        color: '#adb4c4'
                    }
                },
                emphasis: {
                    textStyle: {
                        color: 'rgb(100,0,0)'
                    }
                }
            },
            itemStyle: {
                normal: {
                    areaColor: '#adb4c4',
                    borderColor: '#adb4c4'
                },
                emphasis: {
                    areaColor: '#adb4c4'
                }
            }
        },
        gauge: {
            axisLine: {
                lineStyle: {
                    color: [[0.2, '#adb4c4'], [0.8, '#adb4c4'], [1, '#adb4c4']]
                }
            },
            axisTick: {
                splitNumber: 2,
                length: 5,
                lineStyle: {
                    color: '#adb4c4'
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#adb4c4'
                }
            },
            splitLine: {
                length: '5%',
                lineStyle: {
                    color: '#adb4c4'
                }
            },
            title: {
                offsetCenter: [0, -20]
            }
        }
    };
    echarts.registerTheme('d', theme);
}));