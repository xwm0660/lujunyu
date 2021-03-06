
import 'package:flutter/material.dart';
import 'package:flutter_button/order/order_all_page.dart';
import '../constants/index.dart';

class OrderCenterPage extends StatefulWidget {
  final int index;

  OrderCenterPage({this.index = 0});

  @override
  _OrderCenterPageState createState() => _OrderCenterPageState();
}

class _OrderCenterPageState extends State<OrderCenterPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('订单中心'),
        elevation: 0.0,
      ),
      body: DefaultTabController(
        length: 5,
        initialIndex: widget.index,
        child: Column(
          children: <Widget>[
            KTabBarWidget(),
            Divider(height:5,color: Colors.black26,),
            Expanded(
              child: TabBarView(
                children: <Widget>[
                  OrderAllPage(orderlx: '-1',),
                  OrderAllPage(orderlx: '1',),
                  OrderAllPage(orderlx: '2',),
                  OrderAllPage(orderlx: '3',),
                  OrderAllPage(orderlx: '4',),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}

class KTabBarWidget extends StatelessWidget implements PreferredSizeWidget {
  get preferredSize {
    return Size.fromHeight(30);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      width: MediaQuery.of(context).size.width,
      child: TabBar(
          indicatorColor: KColorConstant.themeColor,
          indicatorSize: TabBarIndicatorSize.label,
          isScrollable: true,
          labelColor: Colors.black,
          tabs: KString.ordertabs
              .map((i) => Container(
              padding: EdgeInsets.all(10),
                    width: MediaQuery.of(context).size.width / 5,
                    height: 50.0,
                    child: new Tab(text: i),
                  ))
              .toList()),
    );
  }
}
