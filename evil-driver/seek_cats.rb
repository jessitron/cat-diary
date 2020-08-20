#!/usr/bin/env ruby

# Outside of Docker, do this:
# chromedriver --whitelisted-ips=$( docker inspect -f "{{ .NetworkSettings.Networks.bridge.IPAddress }}" $(docker ps -q))

require "selenium-webdriver"

class DoThings
  def create_user(cat_name)
    @driver.get "http://localhost:8080/cat/new"
    @driver.find_element(name: "catName").send_keys cat_name, :return
    first_result = @wait.until { @driver.find_element(css: ".new-username") }
    first_result.attribute("textContent")
  end

  def check_for_cat(cat_name)
    puts "Using cat name: " + cat_name
    new_username = create_user(cat_name)
    if (new_username != cat_name)
      puts "------------ EXISTING CAT: " + cat_name
    end
  end

  def run
    @driver = Selenium::WebDriver.for :chrome, {
      #  browser: :remote,
      url: "http://host.docker.internal:9515",
    }
    @wait = Selenium::WebDriver::Wait.new(timeout: 10)
    begin
      File.open("sorted-cat-names.txt").each_line { |n|
        check_for_cat n.chomp
        #  puts "hit enter to continue"
        #  gets
      }
    ensure
      @driver.quit
    end
  end
end

DoThings.new.run
