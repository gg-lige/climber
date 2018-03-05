package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxl.crawl.News_Crawler_2;

import net.jeth.util.DatabaseManager;

@Controller
@RequestMapping(path = "/crawler")
public class CrawlerController {

	private News_Crawler_2 news_Clawler = News_Crawler_2.getInstance();
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String viewSources(Model model) throws Exception {
		List<Map<String, Object>> sources = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> linkRows = DatabaseManager.getInstance().query("SELECT source.name, COUNT(*) FROM Search_Engine_Test_ZXL_2_j, source WHERE Search_Engine_Test_ZXL_2_j.TYPE = source.url GROUP BY source.url");
		for (Map<String, Object> linkRow : linkRows) {
			Map<String, Object> source = new HashMap<String, Object>();
			source.put("name", linkRow.get("name"));
			source.put("count", linkRow.get("COUNT(*)"));
			sources.add(source);
		}
		for (int i = sources.size(); i < 10; i++) {
			Map<String, Object> source = new HashMap<String, Object>();
			source.put("name", "");
			source.put("count", 0);
			sources.add(source);
		}
		model.addAttribute("sources", sources);
		String urls = "";
		List<Map<String, Object>> rows = DatabaseManager.getInstance().query("SELECT URL FROM search_engine.Search_Engine_Test_ZXL_2_j ORDER BY TIME DESC LIMIT 10");
		for (Map<String, Object> row : rows) {
			urls += (String) row.get("URL") + "\n";
		}
		model.addAttribute("urls", urls);
		model.addAttribute("total", DatabaseManager.getInstance().query("SELECT COUNT(*) FROM search_engine.Search_Engine_Test_ZXL_2_j").get(0).get("COUNT(*)"));
		return "crawler/view_sources";
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_HTML_VALUE, params = "command")
	public String updateSources(@RequestParam String command, Model model) throws Exception {
		if (command.equals("start")) {
			synchronized (this) {
				news_Clawler.start_crawler();
			}
		}
		if (command.equals("stop")) {
			synchronized (this) {
				news_Clawler.stop_crawler();
			}
		}
		return "redirect:/crawler";
	}
}
