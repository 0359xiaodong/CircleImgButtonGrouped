CircleButtonGrouped
===================

Android componente que permite criar botões circulares e agrupados com animação de expandir e contrair o grupo

Características

  * Botão circular
  * Agrupamento de botões
  * Animação de expandir e contrair os botões do grupo
  * QuickAction para evento de click no grupo
  * Timeout para contrair botões após expandidoss
  * Label para descrição do grupo
  * Opção de inverter sentido do agrupamento/animações
  
Utilização

     Após adicionar o componente dentro de um RelativeLayout (requer estar layout para funcionar) com seus respectivos parâmetros, chame o método configGroup de cada componente CIBG para configurá-lo.

  * Xml
```
  <com.edp.circlebuttongrouped.CircleImgBtnGroup
      android:id="@+id/cibTop3"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:collapseAtClick="true"
      app:ibCount="3"
      app:ibHeight="@dimen/btSize"
      app:ibWidth="@dimen/btSize"
      app:timeoutToCollapse="2"
      app:imgSrcCIB1="@drawable/ic_bug"
      app:imgSrcCIB2="@drawable/ic_cat"
      app:imgSrcCIB3="@drawable/ic_dog"
      app:imgSrcCIB4="@drawable/ic_elephant"
      app:imgSrcCIB5="@drawable/ic_fish"
      app:imgSrcCIB6="@drawable/ic_turtle"/>
```
  * Activity
```
     RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
     CircleImgBtnGroup cibg1 = (CircleImgBtnGroup) findViewById(R.id.cibg1);
     cibg1.configGroup(this, relativeLayout1);
```

![alt tag](https://raw.githubusercontent.com/edipo2s/CircleButtonGrouped/master/Screenshot_0.png)

![alt tag](https://raw.githubusercontent.com/edipo2s/CircleButtonGrouped/master/Screenshot_1.png)

![alt tag](https://raw.githubusercontent.com/edipo2s/CircleButtonGrouped/master/Screenshot_2.png)

![alt tag](https://raw.githubusercontent.com/edipo2s/CircleButtonGrouped/master/Screenshot_3.png)
