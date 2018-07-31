package javatest;

import java.util.ArrayList;
import java.util.List;

public class bag {
		      static class Item {// 定义一个物品
		    	  String id; // 物品id
		          int size = 0;// 物品所占空间
		          int value = 0;// 物品价值
		
		        static Item newItem(String id, int size, int value) {
		             Item item = new Item();
		              item.id = id;
		            item.size = size;
		             item.value = value;
		             return item;
		        }
		          public String toString() {
		              return this.id;
		         }
		      }
		    static class OkBag { // 定义一个打包方式
		         List<Item> Items = new ArrayList<Item>();// 包里的物品集合
		 
		          OkBag() {
		       }
		
		       int getValue() {// 包中物品的总价值
		            int value = 0;
		            for (Item item : Items) {
		                value += item.value;
		            }
		             return value;
		        };
		 
		        int getSize() {// 包中物品的总大小
		              int size = 0;
		            for (Item item : Items) {
		                 size += item.size;
		            }
		            return size;
		          };
		
		        public String toString() {
		             return String.valueOf(this.getValue()) + " ";
		         }
		      }
		  
		    // 可放入包中的备选物品
		     static Item[] sourceItems = { Item.newItem("4号球", 4, 5), Item.newItem("5号球", 5, 6), Item.newItem("6号球", 6, 7) };
		     static int bagSize = 10; // 包的空间
		     static int itemCount = sourceItems.length; // 物品的数量
		
		     // 保存各种情况下的最优打包方式 第一维度为物品数量从0到itemCount,第二维度为包裹大小从0到bagSize
		      static OkBag[][] okBags = new OkBag[itemCount + 1][bagSize + 1];
		
		      static void init() {
		         for (int i = 0; i < bagSize + 1; i++) {
		             okBags[0][i] = new OkBag();
		          }
		  
		          for (int i = 0; i < itemCount + 1; i++) {
		             okBags[i][0] = new OkBag();
		          }
		      }
		  
		     static void doBag() {
		         init();
		         for (int iItem = 1; iItem <= itemCount; iItem++) {
		             for (int curBagSize = 1; curBagSize <= bagSize; curBagSize++) {
		                  okBags[iItem][curBagSize] = new OkBag();
		                if (sourceItems[iItem - 1].size > curBagSize) {// 当前物品大于包空间.肯定不能放入包中.
		                    okBags[iItem][curBagSize].Items.addAll(okBags[iItem - 1][curBagSize].Items);
		                  } else {
		                     int notIncludeValue = okBags[iItem - 1][curBagSize].getValue();// 不放当前物品包的价值
		                     int freeSize = curBagSize - sourceItems[iItem - 1].size;// 放当前物品包剩余空间
		                     int includeValue = sourceItems[iItem - 1].value + okBags[iItem - 1][freeSize].getValue();// 当前物品价值+放了当前物品后剩余包空间能放物品的价值
		                      if (notIncludeValue < includeValue) {// 放了价值更大就放入.
		                          okBags[iItem][curBagSize].Items.addAll(okBags[iItem - 1][freeSize].Items);
		                         okBags[iItem][curBagSize].Items.add(sourceItems[iItem - 1]);
		                     } else {// 否则不放入当前物品
		                        okBags[iItem][curBagSize].Items.addAll(okBags[iItem - 1][curBagSize].Items);
		                    }
		                  }
		
		            }
		          }
		     }
		  
		    public static void main(String[] args) {
		    	  bag.doBag();
		          for (int i = 0; i < bag.itemCount + 1; i++) {// 打印所有方案中包含的物品
		             for (int j = 0; j < bag.bagSize + 1; j++) {
		                System.out.print(bag.okBags[i][j].Items);
		             }
		            System.out.println("");
		         }
		
		         for (int i = 0; i < bag.itemCount + 1; i++) {// 打印所有方案中包的总价值
		              for (int j = 0; j < bag.bagSize + 1; j++) {
		                 System.out.print(bag.okBags[i][j]);
		             }
		            System.out.println("");
		         }
		 
		        OkBag okBagResult = bag.okBags[bag.itemCount][bag.bagSize];
	            System.out.println("最终结果为:" + okBagResult.Items.toString() + okBagResult);
		
		   }
		 
		 }

