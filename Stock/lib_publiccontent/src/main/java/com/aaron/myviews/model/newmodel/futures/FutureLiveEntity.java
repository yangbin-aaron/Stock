package com.aaron.myviews.model.newmodel.futures;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 持仓直播请求model
 * <p/>
 * Created by Yuan on 16/3/2.
 */
public class FutureLiveEntity {

    private int buyNum;

    private int saleNum;

    private List<User> buy;

    private List<User> sale;

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public void setBuy(List<User> buy) {
        this.buy = buy;
    }

    public void setSale(List<User> sale) {
        this.sale = sale;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public List<User> getBuy() {
        return buy;
    }

    /**
     * 排序盈亏
     *
     * @param bidPrice
     * @param earningsMultiple
     */
    public void sortedBuy(final Double bidPrice, final BigDecimal earningsMultiple) {
        if (buy != null) {
            Collections.sort(buy, new Comparator<User>() {
                @Override
                public int compare(User lhs, User rhs) {
                    return rhs.getBuyGoldEarnings(bidPrice, earningsMultiple)
                            .compareTo(lhs.getBuyGoldEarnings(bidPrice, earningsMultiple));
                }
            });
        }
    }

    /**
     * 根据不重复用户id排序(包括id为null),返回盈利排序的列表
     *
     * @param bidPrice
     * @param earningsMultiple
     * @return
     */
    public List<User> getBuySortedNoRepeat(final Double bidPrice, final BigDecimal earningsMultiple) {
        List<User> users = new ArrayList<>();
        List<User> buyers = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        if (buy != null) {
            for (User user : buy) {
                if (user != null) {
                    buyers.add(user);
                    if (user.getUserId() != null) {
                        if (!userIds.contains(user.getUserId())) {
                            userIds.add(user.getUserId());
                        }
                    } else {
                        double total = user.getBuyGoldEarnings(bidPrice, earningsMultiple).doubleValue();
                        user.setTotalEarning(total);
                        users.add(user);
                    }
                }
            }
            for (int i = 0; i < userIds.size(); i++) {
                User newUser = new User();
                newUser.setUserId(userIds.get(i));
                for (User user : buyers) {
                    if (userIds.get(i) != null) {
                        if (userIds.get(i).equals(user.getUserId())) {
                            newUser.setHeadPic(user.getHeadPic());
                            newUser.setNickName(user.getNickName());

                            double total = user.getBuyGoldEarnings(bidPrice, earningsMultiple).doubleValue()
                                    + newUser.getTotalEarning();
                            newUser.setTotalEarning(total);
                        }
                    }
                }
                users.add(newUser);
            }
        }
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return (int) (rhs.getTotalEarning() - lhs.getTotalEarning());
            }
        });
        return users;
    }

    public List<User> getSale() {
        return sale;
    }

    /**
     * 排序盈亏
     *
     * @param askPrice
     * @param earningsMultiple
     */
    public void sortedSale(final Double askPrice, final BigDecimal earningsMultiple) {
        if (sale != null) {
            Collections.sort(sale, new Comparator<User>() {
                @Override
                public int compare(User lhs, User rhs) {
                    return rhs.getSaleGoldEarnings(askPrice, earningsMultiple)
                            .compareTo(lhs.getSaleGoldEarnings(askPrice, earningsMultiple));
                }
            });
        }
    }

    /**
     * 根据不重复用户id排序(包括id为null),返回盈利排序的列表
     *
     * @param askPrice
     * @param earningsMultiple
     * @return
     */
    public List<User> getSaleSortedNoRepeat(final Double askPrice, final BigDecimal earningsMultiple) {
        List<User> users = new ArrayList<>();
        List<User> salers = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        if (sale != null) {
            for (User user : sale) {
                if (user != null) {
                    salers.add(user);
                    if (user.getUserId() != null) {
                        if (!userIds.contains(user.getUserId())) {
                            userIds.add(user.getUserId());
                        }
                    } else {
                        double total = user.getSaleGoldEarnings(askPrice, earningsMultiple).doubleValue();
                        user.setTotalEarning(total);
                        users.add(user);
                    }
                }
            }
            for (int i = 0; i < userIds.size(); i++) {
                User newUser = new User();
                newUser.setUserId(userIds.get(i));
                for (User user : salers) {
                    if (userIds.get(i) != null) {
                        if (userIds.get(i).equals(user.getUserId())) {
                            newUser.setHeadPic(user.getHeadPic());
                            newUser.setNickName(user.getNickName());

                            double total = user.getSaleGoldEarnings(askPrice, earningsMultiple).doubleValue()
                                    + newUser.getTotalEarning();
                            newUser.setTotalEarning(total);
                        }
                    }
                }
                users.add(newUser);
            }
        }
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return (int) (rhs.getTotalEarning() - lhs.getTotalEarning());
            }
        });
        return users;
    }

    /**
     * 买多总盈利
     *
     * @param bidPrice
     * @param earningsMultiple
     * @return
     */
    public double getBuyersTotalPrice(Double bidPrice, BigDecimal earningsMultiple) {
        double total = 0.0d;

        if (buy != null) {
            for (User user : buy) {
                total += user.getBuyGoldEarnings(bidPrice, earningsMultiple).doubleValue();
            }
        }
        return total;
    }

    /**
     * 买空总盈利
     *
     * @param askPrice
     * @param earningsMultiple
     * @return
     */
    public double getSalersTotalPrice(Double askPrice, BigDecimal earningsMultiple) {
        double total = 0.0d;

        if (sale != null) {
            for (User user : sale) {
                total += user.getSaleGoldEarnings(askPrice, earningsMultiple).doubleValue();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "FutureLiveEntity{" +
                "buyNum=" + buyNum +
                ", saleNum=" + saleNum +
                ", buy=" + buy +
                ", sale=" + sale +
                '}';
    }

    public static class User {
        double price;
        private String headPic;
        double priceSum;
        int hands;
        String nickName;
        String userId;
        double totalEarning;

        public void setTotalEarning(double totalEarning) {
            this.totalEarning = totalEarning;
        }

        public double getTotalEarning() {
            return totalEarning;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setPriceSum(double priceSum) {
            this.priceSum = priceSum;
        }

        public void setHands(int hands) {
            this.hands = hands;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getPrice() {
            return price;
        }

        public double getPriceSum() {
            return priceSum;
        }

        public String getNickName() {
            return nickName;
        }

        public int getHands() {
            return hands;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        @Override
        public String toString() {
            return "User{" +
                    "price=" + price +
                    ", headPic='" + headPic + '\'' +
                    ", priceSum=" + priceSum +
                    ", hands=" + hands +
                    ", nickName='" + nickName + '\'' +
                    ", userId='" + userId + '\'' +
                    ", totalEarning=" + totalEarning +
                    '}';
        }

        /**
         * 计算看多盈利
         *
         * @param bidPrice         买入价
         * @param earningsMultiple
         * @return
         */
        public BigDecimal getBuyGoldEarnings(Double bidPrice, BigDecimal earningsMultiple) {
            BigDecimal bidPriceBigDecimal = new BigDecimal(bidPrice);
            BigDecimal buyPriceBigDecimal = new BigDecimal(price);
            BigDecimal num = new BigDecimal(hands);
            return (bidPriceBigDecimal.subtract(buyPriceBigDecimal)).multiply(earningsMultiple).multiply(num);
        }

        /**
         * 计算看空盈利
         *
         * @param askPrice         买入价
         * @param earningsMultiple
         * @return
         */
        public BigDecimal getSaleGoldEarnings(Double askPrice, BigDecimal earningsMultiple) {
            BigDecimal askPriceBigDecimal = new BigDecimal(askPrice);
            BigDecimal buyPriceBigDecimal = new BigDecimal(price);
            BigDecimal num = new BigDecimal(hands);
            return (buyPriceBigDecimal.subtract(askPriceBigDecimal)).multiply(earningsMultiple).multiply(num);
        }
    }

}
