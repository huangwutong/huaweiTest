package javatest;

import java.util.ArrayList;
import java.util.List;

public class bag {
		      static class Item {// ����һ����Ʒ
		    	  String id; // ��Ʒid
		          int size = 0;// ��Ʒ��ռ�ռ�
		          int value = 0;// ��Ʒ��ֵ
		
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
		    static class OkBag { // ����һ�������ʽ
		         List<Item> Items = new ArrayList<Item>();// �������Ʒ����
		 
		          OkBag() {
		       }
		
		       int getValue() {// ������Ʒ���ܼ�ֵ
		            int value = 0;
		            for (Item item : Items) {
		                value += item.value;
		            }
		             return value;
		        };
		 
		        int getSize() {// ������Ʒ���ܴ�С
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
		  
		    // �ɷ�����еı�ѡ��Ʒ
		     static Item[] sourceItems = { Item.newItem("4����", 4, 5), Item.newItem("5����", 5, 6), Item.newItem("6����", 6, 7) };
		     static int bagSize = 10; // ���Ŀռ�
		     static int itemCount = sourceItems.length; // ��Ʒ������
		
		     // �����������µ����Ŵ����ʽ ��һά��Ϊ��Ʒ������0��itemCount,�ڶ�ά��Ϊ������С��0��bagSize
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
		                if (sourceItems[iItem - 1].size > curBagSize) {// ��ǰ��Ʒ���ڰ��ռ�.�϶����ܷ������.
		                    okBags[iItem][curBagSize].Items.addAll(okBags[iItem - 1][curBagSize].Items);
		                  } else {
		                     int notIncludeValue = okBags[iItem - 1][curBagSize].getValue();// ���ŵ�ǰ��Ʒ���ļ�ֵ
		                     int freeSize = curBagSize - sourceItems[iItem - 1].size;// �ŵ�ǰ��Ʒ��ʣ��ռ�
		                     int includeValue = sourceItems[iItem - 1].value + okBags[iItem - 1][freeSize].getValue();// ��ǰ��Ʒ��ֵ+���˵�ǰ��Ʒ��ʣ����ռ��ܷ���Ʒ�ļ�ֵ
		                      if (notIncludeValue < includeValue) {// ���˼�ֵ����ͷ���.
		                          okBags[iItem][curBagSize].Items.addAll(okBags[iItem - 1][freeSize].Items);
		                         okBags[iItem][curBagSize].Items.add(sourceItems[iItem - 1]);
		                     } else {// ���򲻷��뵱ǰ��Ʒ
		                        okBags[iItem][curBagSize].Items.addAll(okBags[iItem - 1][curBagSize].Items);
		                    }
		                  }
		
		            }
		          }
		     }
		  
		    public static void main(String[] args) {
		    	  bag.doBag();
		          for (int i = 0; i < bag.itemCount + 1; i++) {// ��ӡ���з����а�������Ʒ
		             for (int j = 0; j < bag.bagSize + 1; j++) {
		                System.out.print(bag.okBags[i][j].Items);
		             }
		            System.out.println("");
		         }
		
		         for (int i = 0; i < bag.itemCount + 1; i++) {// ��ӡ���з����а����ܼ�ֵ
		              for (int j = 0; j < bag.bagSize + 1; j++) {
		                 System.out.print(bag.okBags[i][j]);
		             }
		            System.out.println("");
		         }
		 
		        OkBag okBagResult = bag.okBags[bag.itemCount][bag.bagSize];
	            System.out.println("���ս��Ϊ:" + okBagResult.Items.toString() + okBagResult);
		
		   }
		 
		 }

