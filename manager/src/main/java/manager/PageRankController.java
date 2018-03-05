package manager;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Graph;
import entity.Link;
import entity.Node;
import net.jeth.util.DatabaseManager;

@Controller
public class PageRankController {

	@RequestMapping(path = "/pagerank", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String pagerank(Model model) throws Exception {
		return "pagerank/pagerank";
	}

	@RequestMapping(path = "/pagerank/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Graph json(Model model) throws Exception {
		Set<Node> nodes = new HashSet<Node>();
		Set<Link> links = new HashSet<Link>();
		List<Map<String, Object>> rows = DatabaseManager.getInstance().query("SELECT srcid, dstid FROM search_engine.TOP500_EDGE_LG");
		for (Map<String, Object> row : rows) {
			nodes.add(new Node((int) row.get("srcid")));
			nodes.add(new Node((int) row.get("dstid")));
			links.add(new Link((int) row.get("srcid"), (int) row.get("dstid")));
		}
		return new Graph(nodes, links);
	}
}
