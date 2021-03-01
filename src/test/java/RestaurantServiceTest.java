import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;


class RestaurantServiceTest {

    static RestaurantService service = new RestaurantService();
    static Restaurant restaurant;

    // It will trigger before all tests
    @BeforeAll
    public static void beforeAll() {
        System.out.println("beforeAll");
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        Restaurant searchedRestaurant = service.findRestaurantByName("Ruskin's cafe");
        assertNotNull(searchedRestaurant);
    }

    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class, () -> {
            service.findRestaurantByName("Vaibhav's cafe");
        });
    }

    @Test
    public void display_menu_of_a_restaurant() throws restaurantNotFoundException {
        Restaurant searchedRestaurant = service.findRestaurantByName("Ruskin's cafe");
        List<Item> menu = searchedRestaurant.getMenu();
        assertEquals("Latte", menu.get(0).getName());
    }

    @Test
    public void throw_item_not_found_exception_while_calculating_the_order_value () {
        Restaurant searchedRestaurant = service.findRestaurantByName("Ruskin's cafe");
        List<String> items = new ArrayList<String>();
        items.add("Invalid item name");
        assertThrows(itemNotFoundException.class, () -> searchedRestaurant.calculateOrderValue(items));
    }

    @Test
    public void calculate_the_order_value_of_items_when_the_item_names_are_provided() {
        Restaurant searchedRestaurant = service.findRestaurantByName("Ruskin's cafe");
        List<String> items = new ArrayList<String>();
        items.add("Latte");
        items.add("Cold coffee");
        int totalOrderValue = searchedRestaurant.calculateOrderValue(items);
        System.out.println(totalOrderValue);
        assertEquals(180, totalOrderValue);
    }

    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }

    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

}