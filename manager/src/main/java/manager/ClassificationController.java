package manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.jeth.util.DatabaseManager;

@Controller
@RequestMapping(path = "/classification")
public class ClassificationController {

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String classification(Model model) throws ClassNotFoundException, SQLException {
		List<Map<String, Object>> rows = DatabaseManager.getInstance().query("SELECT `TYPE`, RECALL, `PRECISION`, NUM FROM search_engine.CLASSIFY");
		String[] names = new String[10];
		names[0] = (String) rows.get(0).get("TYPE");
		names[1] = (String) rows.get(1).get("TYPE");
		names[2] = (String) rows.get(2).get("TYPE");
		names[3] = (String) rows.get(3).get("TYPE");
		names[4] = (String) rows.get(4).get("TYPE");
		names[5] = (String) rows.get(5).get("TYPE");
		names[6] = (String) rows.get(6).get("TYPE");
		names[7] = "其他";
		names[8] = "其他";
		names[9] = "其他";
		model.addAttribute("names", names);
		int[] counts = new int[10];
		counts[0] = (int) rows.get(0).get("NUM");
		counts[1] = (int) rows.get(1).get("NUM");
		counts[2] = (int) rows.get(2).get("NUM");
		counts[3] = (int) rows.get(3).get("NUM");
		counts[4] = (int) rows.get(4).get("NUM");
		counts[5] = (int) rows.get(5).get("NUM");
		counts[6] = (int) rows.get(6).get("NUM");
		counts[7] = 0;
		counts[8] = 0;
		counts[9] = 0;
		model.addAttribute("counts", counts);
		String[] recall = new String[10];
		recall[0] = (String) rows.get(0).get("RECALL");
		recall[1] = (String) rows.get(1).get("RECALL");
		recall[2] = (String) rows.get(2).get("RECALL");
		recall[3] = (String) rows.get(3).get("RECALL");
		recall[4] = (String) rows.get(4).get("RECALL");
		recall[5] = (String) rows.get(5).get("RECALL");
		recall[6] = (String) rows.get(6).get("RECALL");
		recall[7] = "";
		recall[8] = "";
		recall[9] = "";
		model.addAttribute("recall", recall);
		String[] precision = new String[10];
		precision[0] = (String) rows.get(0).get("PRECISION");
		precision[1] = (String) rows.get(1).get("PRECISION");
		precision[2] = (String) rows.get(2).get("PRECISION");
		precision[3] = (String) rows.get(3).get("PRECISION");
		precision[4] = (String) rows.get(4).get("PRECISION");
		precision[5] = (String) rows.get(5).get("PRECISION");
		precision[6] = (String) rows.get(6).get("PRECISION");
		precision[7] = "";
		precision[8] = "";
		precision[9] = "";
		model.addAttribute("precision", precision);
		return "classification/classification";
	}
}
